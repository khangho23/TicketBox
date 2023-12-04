package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.SecurityConfig;
import com.example.demo.dao.StaffDao;
import com.example.demo.entity.Staff;
import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.enums.RequestStatusEnum;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.AccountModel;
import com.example.demo.model.StaffUpdatePasswordModel;

@Service
public class StaffService {
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private SecurityConfig securityConfig;
	@Autowired
	PasswordEncoder passwordEncoder;

	public RequestStatusEnum insert(Staff staff) throws InvalidRequestParameterException {
		staff.setPassword(securityConfig.passwordEncoder().encode(staff.getPassword()));
		return (staffDao.insert(staff) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public Optional<Staff> loginA(AccountModel account) throws InvalidRequestParameterException {
		Optional<Staff> staff = staffDao.findByEmail(account.getEmail());
		if (!staff.isEmpty()) {
			if (passwordEncoder.matches(account.getPassword(), staff.get().getPassword())) {
				if(staff.get().getRole()>2){
					throw new InvalidRequestParameterException("Bạn không có quyền truy cập trang", RequestParameterEnum.WRONG);
				}
				return staff;
			} else {
				throw new InvalidRequestParameterException("Password", RequestParameterEnum.WRONG);
			}
		} else {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.NOT_EXISTS);
		}
	}

	public Optional<Staff> loginE(AccountModel account) throws InvalidRequestParameterException {
		Optional<Staff> staff = staffDao.findByEmail(account.getEmail());
		if (!staff.isEmpty()) {
			if (account.getPassword() != staff.get().getPassword()) {
				if(staff.get().getRole()<3){
					throw new InvalidRequestParameterException("Bạn không có quyền truy cập trang", RequestParameterEnum.WRONG);
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
}
