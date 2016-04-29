package com.eda.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eda.rest.model.dao.IHomeDao;

@Service
public class HomeService implements IHomeService {
	@Autowired
	IHomeDao homeDao;

	@Override
	public String getName() {

		return homeDao.getName();
	}

}
