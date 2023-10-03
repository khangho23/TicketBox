package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LanguageDao;
import com.example.demo.entity.Language;

@Service
public class LanguageService {
	@Autowired
	private LanguageDao languageDao;
	
	public List<Language> findAll(){
		return languageDao.findAll();
	}
}
