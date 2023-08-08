package com.hoaxify.ws.shared;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hoaxify.ws.file.FileService;

public class FileTypeValidator implements ConstraintValidator<FileType,String>{
	
	
	@Autowired
	FileService fileService;
	String[] types;
	@Override
	public void initialize(FileType constraintAnnotation) {
		types = constraintAnnotation.types(); //Annotation'un field'ını çektik
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null || value.isEmpty()) {
			return true; //Kullanıcı illa fotoğraf yüklemek zorunda değil
		}
		String fileType = fileService.detectType(value);
		for(String supportedType : this.types) {
			if(fileType.contains(supportedType)) {
				return true;
			}
		}
		//Mesajı düzgün bir biçimde yazmak
		String supportedTypes = Arrays.stream(types).collect(Collectors.joining(", ")); //elemanları , ile birleştirip tek bir string oluştur
		context.disableDefaultConstraintViolation(); //default mesajı engelle
		context.unwrap(HibernateConstraintValidatorContext.class)
		.addMessageParameter("types", supportedTypes) //types adlı parametreyi gördüğün yere bu supportedTypes String'ini yaz
		.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()) //default template'i kullan
		.addConstraintViolation(); //bunu bilmiyorum
		
		return false;
	}

}
