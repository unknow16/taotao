package com.taotao.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IPictureService {

	public Map uploadPicture(MultipartFile multipartFile);
}
