package com.hoaxify.ws.hoax;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoaxService {

	@Autowired
	HoaxRepository hoaxRepository;

	public void save(Hoax hoax) {
		hoax.setTimestamp(new Date());
		hoaxRepository.save(hoax);	
	}
}