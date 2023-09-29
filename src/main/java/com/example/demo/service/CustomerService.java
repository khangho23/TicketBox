package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.admin.controller.enums.RequestStatusEnum;
import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.Customer;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.AccountModel;
import com.example.demo.model.MailInfoModel;
import com.example.demo.util.FileUtils;

import jakarta.mail.MessagingException;

@Service
public class CustomerService implements BaseService<Customer, Integer> {
    @Autowired
    CustomerDao customerDao;
    @Autowired
    EmailService emailService;
    @Autowired
    S3Service s3Service;

    // The name of an existing bucket, or access point ARN, to which the new object will be uploaded
    final String BUCKET_NAME = "zuhot-cinema-images";

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

    public Customer authenticator(String email, String password) throws InvalidRequestParameterException {
        Customer customer = customerDao.findByEmail(email)
                .orElseThrow(() -> new InvalidRequestParameterException("Email", RequestParameterEnum.NOT_EXISTS));
        if (customer.isActive()) {
            if (customer.getPassword().equals(password)) {
                return customer;
            } else {
                throw new InvalidRequestParameterException("Password", RequestParameterEnum.WRONG);
            }
        } else {
            throw new InvalidRequestParameterException("Email", RequestParameterEnum.NOT_EXISTS);
        }
    }

    public RequestStatusEnum insert(Customer customer) throws InvalidRequestParameterException {
        if (customerDao.findByEmail(customer.getEmail()).isPresent()) {
            throw new InvalidRequestParameterException("", RequestParameterEnum.EXISTS);
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

    public String updateProfile(Customer customer) {
        customerDao.updateProfile(customer);
        return RequestStatusEnum.SUCCESS.getResponse();
    }

    public String updateAvatar(Integer customerId, MultipartFile multipartFile)
            throws IOException, InvalidRequestParameterException {
        Optional<Customer> customer = customerDao.findById(customerId);
        String fileNameExists;

        String folder = "avatar-user/";

        // Type of file
        String extension = FileUtils.getExtension(multipartFile.getOriginalFilename());

        String fileName = "cus" + customerId;
        // The key under which to store the new object
        String key = folder + fileName + "." + extension;

        InputStream inputStream = multipartFile.getInputStream();

        // Set content-type for Metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/" + extension);

        // Check if a avatar exists
        if (customer.get().getAvatar() != null) {
            String avatar = customer.get().getAvatar();
            fileNameExists = avatar.substring(0, customer.get().getAvatar().indexOf("."));

            if (fileNameExists.equals(fileName))
                s3Service.deleteFile(BUCKET_NAME, folder + avatar);
        }

        // Upload avatar to S3 bucket
        s3Service.saveFile(BUCKET_NAME, key, inputStream, objectMetadata);

        // Update customer avatar
        customer.get().setAvatar("cus" + customerId + "." + extension);
        customerDao.updateAvatar(customer.get());
        return RequestStatusEnum.SUCCESS.getResponse();
    }

    public String deleteAvatar(Optional<Integer> customerId, Optional<String> avatar) throws InvalidRequestParameterException {
        // Check if the customer id field exists
        if (customerId.isEmpty() || avatar.isEmpty())
            throw new InvalidRequestParameterException("Delete Avatar", RequestParameterEnum.NOT_EXISTS);

//        Optional<Customer> customer = customerDao.findById(customerId.get());
        String folder = "avatar-user/";
        String key = avatar.get();

        // Delete avatar on AWS and Database
        Customer customer = new Customer();
        s3Service.deleteFile(BUCKET_NAME, folder + key);
        customer.setId(customerId.get());
        customer.setAvatar(null);
        customerDao.updateAvatar(customer);

        return RequestStatusEnum.SUCCESS.getResponse();
    }

    public String updatePassword(AccountModel account) throws InvalidRequestParameterException {
        Customer customer = customerDao.findById(account.getCustomerId()).get();

        if (!customer.getPassword().equals(account.getPassword())) {
            throw new InvalidRequestParameterException("Password", RequestParameterEnum.WRONG);
        }

        customer.setPassword(account.getNewPassword());
        customerDao.updatePassword(customer);
        return RequestStatusEnum.SUCCESS.getResponse();
    }

    public String registration(Customer customer) throws InvalidRequestParameterException {
        Optional<Customer> us = customerDao.findByEmail(customer.getEmail());
        if (us.isPresent()) {
            if (us.get().getToken() != null)
                // Exists Token
                throw new InvalidRequestParameterException("Customer", RequestParameterEnum.EXISTS);
        }
        try {
            return (emailService.sendCode(new MailInfoModel(customer.getEmail(),
                    "Mã xác minh tài khoản của bạn trên Zuhot Cinema", customer)));
        } catch (MessagingException ex) {
            throw new InvalidRequestParameterException("Email", RequestParameterEnum.WRONG);
        }
    }

    public RequestStatusEnum registrationConfirm(String token) throws InvalidRequestParameterException {
        Customer customer = customerDao.findByToken(token)
                .orElseThrow(() -> new InvalidRequestParameterException("Token", RequestParameterEnum.NOT_EXISTS));
        if (!customer.getToken().equals(token)) {
            throw new InvalidRequestParameterException("Token", RequestParameterEnum.WRONG);
        }
        customer.setActive(true);
        return (customerDao.updateActive(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
    }
}
