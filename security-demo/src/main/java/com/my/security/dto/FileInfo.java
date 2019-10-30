package com.my.security.dto;

import lombok.Data;

@Data
public class FileInfo {

	private int id;
	private String path;

	public FileInfo(String path) {
		this.path = path;
	}

}
