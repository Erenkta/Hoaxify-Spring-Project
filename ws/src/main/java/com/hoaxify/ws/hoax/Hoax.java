package com.hoaxify.ws.hoax;

import java.security.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Hoax {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Size(min=5,max=1000)
	@Column(length = 1000) //eğer string ise varsayılan 255 bunu 1000 e çektik ki max değer de düzgün çalışsın
	private String content;	
	
	@Temporal(TemporalType.DATE) // sadece tarih tutsun istedik 
	private Date timestamp;
	
}
