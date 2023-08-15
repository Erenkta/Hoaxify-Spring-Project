package com.hoaxify.ws.file.vm;

import com.hoaxify.ws.file.FileAttachment;

import lombok.Data;

@Data
public class FileAttachmentVM {
	private String name;
	private String fileType;
	
	public FileAttachmentVM(FileAttachment attachment) {
		this.setName(attachment.getName());
		this.fileType = attachment.getFileType();
	}
}
