package com.hoaxify.ws.hoax;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoaxify.ws.user.User;

@Service(value = "hoaxSecurity")
public class HoaxSecurityService {
	@Autowired
	HoaxRepository hoaxRepository;
	
	public boolean isAllowedToDelete(long id,User loggedInUser) {
		Optional<Hoax> hoaxInDb = hoaxRepository.findById(id);
		if(!hoaxInDb.isPresent()) { //Öyle bir hoax yoksa
			return false;
		}
		Hoax hoax = hoaxInDb.get();
		if(hoax.getUser().getId() != loggedInUser.getId()){ //Hoax var ama kullanıcıya ait değilse
			return false;
		}
		return true;
	}

}
