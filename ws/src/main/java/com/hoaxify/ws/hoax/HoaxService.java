package com.hoaxify.ws.hoax;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserService;



@Service
public class HoaxService {

	@Autowired
	HoaxRepository hoaxRepository;

	@Autowired
	UserService userService;
	

	public void save(Hoax hoax,User user) {
		hoax.setTimestamp(new Date());
		hoax.setUser(user);
		hoaxRepository.save(hoax);	
	}

	public Page<Hoax> getHoaxes(Pageable page) {
		return hoaxRepository.findAll(page);
	}

	public Page<Hoax> getUserHoax(String username,Pageable page) {	
		User inDB  = userService.getByUsername(username);
		return hoaxRepository.findByUser(inDB, page);
	}

	public Page<Hoax> getOldHoaxes(long id, Pageable page) {
		return hoaxRepository.findByIdLessThan(id, page);
	
	}

	public Page<Hoax> getOldUserHoax(long id, String username, Pageable page) {
		User inDB  = userService.getByUsername(username);
		return hoaxRepository.findByIdLessThanAndUser(id,inDB,page);
	}
}
