package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.SecurityConfig;
import com.example.demo.dao.StaffDao;
import com.example.demo.dto.StaffDto;
import com.example.demo.dto.StaffRespDto;
import com.example.demo.entity.Staff;
import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.enums.RequestStatusEnum;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.AccountModel;
import com.example.demo.model.MailInfoModel;
import com.example.demo.model.StaffUpdatePasswordModel;

import jakarta.mail.MessagingException;

@Service
public class StaffService {
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private SecurityConfig securityConfig;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private EmailService emailService;

	public RequestStatusEnum insert(Staff staff) throws InvalidRequestParameterException {
		if (staffDao.findById(staff.getId()).isPresent()) {
			throw new InvalidRequestParameterException("Mã nhân viên", RequestParameterEnum.EXISTS);
		} else if (staffDao.findByEmail(staff.getEmail()).isPresent()) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.EXISTS);
		} else {
			try {
				emailService.sendPassword(new MailInfoModel(staff.getEmail(), "Mật khẩu tại Zuhot Cinema", staff));
				staff.setPassword(securityConfig.passwordEncoder().encode(staff.getPassword()));
			} catch (MessagingException e) {
				throw new InvalidRequestParameterException("Email", RequestParameterEnum.INVALID_TYPE);
			}
			return (staffDao.insert(staff) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
		}
	}

	public Optional<Staff> loginA(AccountModel account) throws InvalidRequestParameterException {
		Optional<Staff> staff = staffDao.findByEmail(account.getEmail());
		if (!staff.isEmpty()) {
			if (passwordEncoder.matches(account.getPassword(), staff.get().getPassword())) {
				if (staff.get().getRole() > 2) {
					throw new InvalidRequestParameterException("Bạn không có quyền truy cập trang",
							RequestParameterEnum.WRONG);
				}
				return staff;
			} else {
				throw new InvalidRequestParameterException("Password", RequestParameterEnum.WRONG);
			}
		} else {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.NOT_EXISTS);
		}
	}

	public Optional<StaffRespDto> loginE(AccountModel account) throws InvalidRequestParameterException {
		Optional<StaffRespDto> staff = staffDao.check(account.getEmail());
		if (!staff.isEmpty()) {
			if (account.getPassword() != staff.get().getPassword()) {
				if (staff.get().getRole() < 3) {
					throw new InvalidRequestParameterException("Bạn không có quyền truy cập trang",
							RequestParameterEnum.WRONG);
				}
				return staff;
			} else {
				throw new InvalidRequestParameterException("Password", RequestParameterEnum.WRONG);
			}
		} else {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.NOT_EXISTS);
		}
	}

	public Optional<Staff> findById(String id) throws InvalidRequestParameterException {
		Optional<Staff> staff = staffDao.findById(id);
		if (staff.isEmpty()) {
			throw new InvalidRequestParameterException("id", RequestParameterEnum.WRONG);
		}
		return staff;
	}

	public RequestStatusEnum update(Staff staff) throws InvalidRequestParameterException {
		staff.setPassword(securityConfig.passwordEncoder().encode(staff.getPassword()));
		return (staffDao.update(staff) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public RequestStatusEnum updatePassword(StaffUpdatePasswordModel model) throws InvalidRequestParameterException {
		Optional<Staff> obj = staffDao.findById(model.getId());
		if (obj.isEmpty()) {
			throw new InvalidRequestParameterException("id", RequestParameterEnum.WRONG);
		}
		if (!model.getPasswordOld().equals(obj.get().getPassword())) {
			throw new InvalidRequestParameterException("Mật khẩu cũ không chính xác", RequestParameterEnum.WRONG);
		}
		return (staffDao.updatePassword(model) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public List<StaffDto> findAll() {
		return staffDao.findAll();
	}

	public RequestStatusEnum updateStatus(Staff staff) throws InvalidRequestParameterException {
		return (staffDao.updateStatus(staffDao.findById(staff.getId())
				.orElseThrow(() -> new InvalidRequestParameterException("Id", RequestParameterEnum.NOT_FOUND))) == 1
						? RequestStatusEnum.SUCCESS
						: RequestStatusEnum.FAILURE);
	}
}
