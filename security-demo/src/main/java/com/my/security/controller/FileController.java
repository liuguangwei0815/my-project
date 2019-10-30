package com.my.security.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.security.dto.FileInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传下载
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

	private String path = "E:\\security-my-works\\my-security-oauth2\\security-demo\\src\\main\\java\\com\\my\\security";

	@PostMapping
	public FileInfo uplaod(MultipartFile multipartFile) throws IllegalStateException, IOException {
		log.info("MultipartFile：{}", ReflectionToStringBuilder.reflectionToString(multipartFile));
		File file = new File(path, multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		return new FileInfo(path);
	}

	@GetMapping("/{id:\\d+}")
	public void dowload(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
		File file = new File(path, "test.txt");
		try (FileInputStream fileInputStream = new FileInputStream(file);
				ServletOutputStream out = response.getOutputStream();) {
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition", "attachment;filename=test.txt");
			IOUtils.copy(fileInputStream, out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
