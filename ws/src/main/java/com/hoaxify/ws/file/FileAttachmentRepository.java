package com.hoaxify.ws.file;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoaxify.ws.user.User;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
	List<FileAttachment> findByDateBeforeAndHoaxIsNull(Date date); //bizim vereceğimiz tarihten önce olup bir hoax'a ait olmayanları bul
	List<FileAttachment> findByHoaxUser(User user); //user'a ait olan hoax'daki file attachment'a eriş
}
