package com.hoaxify.ws.user;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	Page<User> findByUsernameNot(String username,Pageable page);
	/*	@Transactional //transaction yoksa bunu oluşturuyor
	void deleteByUsername(String username);*/
	
	
	
	
	/*
	@Query(value = "Select u from User u") //Kendimiz yazdık demek //Burada Query içine sorguyu JPQL ile yazacağız //Get all yaptık
	
	Page<UserProjection> getAllUsersProjection(Pageable page); //Page verdik çünkü pagination'a ihtiyacimiz var
	
	Projection dönmesini istersek sürekli method eklememiz gerekicekti
	Bunun yerine daha iyi bir yöntem yapacağız (DTO (VM) ) yapacağız
	*/
}
