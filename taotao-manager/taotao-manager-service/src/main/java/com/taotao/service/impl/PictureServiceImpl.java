package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.IPictureService;

@Service
public class PictureServiceImpl implements IPictureService {

	@Value("${FTP_IP}")
	private String FTP_IP;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	@Override
	public Map uploadPicture(MultipartFile multipartFile) {
		String oldName = multipartFile.getOriginalFilename();
		String newName = IDUtils.genImageName();
		newName = newName + oldName.substring(oldName.lastIndexOf("."));
		String filePath = new DateTime().toString("/yyyy/MM/dd");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean isSuccess = FtpUtil.uploadFile(FTP_IP, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, filePath, newName, multipartFile.getInputStream());
			if(!isSuccess) {
				map.put("error", 1);
				map.put("message", "图片上传失败");
				return map;
			}
			map.put("error", 0);
			map.put("url", IMAGE_BASE_URL + filePath + "/" + newName);
			return map;
		} catch (IOException e) {
			map.put("error", 1);
			map.put("message", "图片上传发生异常");
		}
		return null;
	}
}
