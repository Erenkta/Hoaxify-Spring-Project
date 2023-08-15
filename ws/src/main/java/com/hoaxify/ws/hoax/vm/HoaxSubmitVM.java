package com.hoaxify.ws.hoax.vm;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class HoaxSubmitVM {
	
	@Size(min=5,max=1000)
	private String content;	
	
	private long attachmentId;
}
