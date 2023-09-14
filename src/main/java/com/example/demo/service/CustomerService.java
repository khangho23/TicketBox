package com.example.demo.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.enums.RequestParameterEnum;
import com.example.demo.common.enums.RequestStatusEnum;
import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.Customer;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.MailInfoModel;
import com.example.demo.util.FileUtils;

import jakarta.mail.MessagingException;

@Service
public class CustomerService implements BaseService<Customer, Integer> {
	private static final String PATH_STATIC = System.getProperty("user.dir")
			+ "/src/main/resources/static/customers_avatar/";

	@Autowired
	CustomerDao customerDao;
	@Autowired
	EmailService emailService;

	@Override
	public List<Customer> findAll() {
		return customerDao.findAll();
	}

	@Override
	public Optional<Customer> findById(Integer id) {
		return customerDao.findById(id);
	}

	public Optional<Customer> findByEmail(String email) {
		return customerDao.findByEmail(email);
	}

	public Customer Authenticator(String email, String password) throws InvalidRequestParameterException {
		Customer customer = customerDao.findByEmail(email)
				.orElseThrow(() -> new InvalidRequestParameterException(RequestParameterEnum.NOT_EXISTS));
		if (customer.getPassword().equals(password)) {
			return customer;
		} else {
			throw new InvalidRequestParameterException(RequestParameterEnum.WRONG);
		}
	}

	public RequestStatusEnum insert(Customer customer) throws InvalidRequestParameterException {
		if (customerDao.findByEmail(customer.getEmail()).isPresent()) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.EXISTS);
		}
		return (customerDao.insert(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public RequestStatusEnum delete(Customer customer) {
		return (customerDao.delete(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public RequestStatusEnum updateToken(Customer customer) {
		return (customerDao.updateToken(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public RequestStatusEnum updateActive(Customer customer) {
		return (customerDao.updateActive(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public Optional<Customer> findByToken(String token) {
		return customerDao.findByToken(token);
	}

	public String registration(Customer customer) throws InvalidRequestParameterException {
		Optional<Customer> us = customerDao.findByEmail(customer.getEmail());
		if (us.isPresent()) {
			if (us.get().getToken() != null)
				// Exists Token
				throw new InvalidRequestParameterException(RequestParameterEnum.EXISTS);
		}
		try {
			return (emailService.sendCode(new MailInfoModel(customer.getEmail(),
					"Mã xác minh tài khoản của bạn trên Zuhot Cinema", customer)));
		} catch (MessagingException ex) {
			throw new InvalidRequestParameterException(RequestParameterEnum.WRONG);
		}
	}

	public RequestStatusEnum registrationConfirm(String OTP) throws InvalidRequestParameterException {
		Customer customer = customerDao.findByToken(OTP)
				.orElseThrow(() -> new InvalidRequestParameterException(RequestParameterEnum.NOT_EXISTS));
		if (customer.getToken().equals(OTP)) {
			customer.setActive(true);
			return (customerDao.updateActive(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
		} else {
			throw new InvalidRequestParameterException(RequestParameterEnum.WRONG);
		}
	}

	public RequestStatusEnum updateInformation(Customer customer) {
		try {
			customerDao.updateInformation(customer);
			return RequestStatusEnum.SUCCESS;
		} catch (Exception ex) {
			ex.printStackTrace();
			return RequestStatusEnum.FAILURE;
		}
	}

	public RequestStatusEnum updateAvatar(Integer customerId, MultipartFile multipartFile) throws IOException {
		Optional<Customer> customer = customerDao.findById(customerId);

		if (multipartFile == null || multipartFile.isEmpty()) {
			return RequestStatusEnum.FAILURE; // No file uploaded
		}

		try {
			// Convert multipart -> file
			File file = FileUtils.multipartFileToFileConverter(PATH_STATIC, multipartFile);

			// Check if the conversion was successful
			if (file == null || !file.exists()) {
				System.out.print("here");
				return RequestStatusEnum.FAILURE; // Conversion or file not found
			}

			// Read the image
			BufferedImage img = ImageIO.read(file);
			String extension = FileUtils.getExtension(multipartFile.getOriginalFilename());

			// Define the destination file
			File destinationFile = new File(PATH_STATIC + "cus" + customerId + "." + extension);
			String destinationFileName = destinationFile.getName().substring(0, destinationFile.getName().indexOf("."));
			System.out.println("asss: "+destinationFile.exists());
			// Write the image to the destination
			ImageIO.write(img, extension, destinationFile);
			
			// Check if the destination file was created
			if (destinationFile.getName().contains(destinationFileName)) {
				// Delete the temporary file
				file.delete();
				
				// Delete old avatar
//				Path fileToDeletePath = Paths.get(PATH_STATIC + destinationFileName + "." + "png");
//				Files.delete(fileToDeletePath);

				// Update customer avatar
				customer.get().setAvatar("cus" + customerId + "." + extension);
				customerDao.updateAvatar(customer.get());
				return RequestStatusEnum.SUCCESS;
			} else {
				return RequestStatusEnum.FAILURE; // Failed to save the image
			}
		} catch (IOException e) {
			// Handle IO exception, log the error
			e.printStackTrace();
			return RequestStatusEnum.FAILURE;
		}
	}
}
