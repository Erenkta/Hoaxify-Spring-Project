package com.hoaxify.ws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hoaxify.ws.configuration.AppConfiguration;

@Service
public class FileService {
	
	AppConfiguration appConfiguration;
	
	Tika tika;
	
	FileAttachmentRepository fileAttachmentRepository;
	@Autowired
	public FileService(AppConfiguration appConfiguration,FileAttachmentRepository fileAttachmentRepository) {
		super();
		this.appConfiguration = appConfiguration;
		this.tika = new Tika();
		this.fileAttachmentRepository = fileAttachmentRepository;
	}
	
	public String writeBase64EncodedStringToFile(String image) throws IOException {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getProfileStoragePath() + "/" + fileName);
		OutputStream outputStream = new FileOutputStream(target);
		
		byte[] base64encoded = Base64.getDecoder().decode(image);
				
		outputStream.write(base64encoded);
		outputStream.close();
		return fileName;
	}
	
	public String generateRandomName() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public void deleteProfileImage(String oldImageName) {
		if(oldImageName == null) {
			return;
		}
		deleteFile(Paths.get(appConfiguration.getProfileStoragePath(), oldImageName));
	}
	public void deleteAttachmentImage(String oldImageName) {
		if(oldImageName == null) {
			return;
		}
		deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(), oldImageName));
	}
	private void deleteFile(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String detectType(String base64) {
		byte[] base64encoded = Base64.getDecoder().decode(base64);
		return detectType(base64encoded);
	}
	public String detectType(byte[] arr) {
		return tika.detect(arr);
	}

	public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getAttachmentStoragePath() + "/" + fileName);
		String fileType = null;
		try {
			OutputStream outputStream = new FileOutputStream(target);
			outputStream.write(multipartFile.getBytes());
			outputStream.close();
			fileType = detectType(multipartFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileAttachment attachment = new FileAttachment();
		attachment.setName(fileName);
		attachment.setDate(new Date());
		attachment.setFileType(fileType);
		return fileAttachmentRepository.save(attachment);
	}

}
