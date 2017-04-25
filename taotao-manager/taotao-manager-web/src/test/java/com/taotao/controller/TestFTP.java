package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.taotao.common.utils.FtpUtil;

public class TestFTP {

	public static void main(String[] args) throws Exception {
		/*
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.11.140", 21);
		ftpClient.login("ftpuser", "123456");
		FileInputStream inputStream = new FileInputStream(new File("D:\\Freedom\\2.jpg"));
		ftpClient.changeWorkingDirectory("/usr/local/nginx/html/images");
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.storeFile("123.jpg", inputStream);
		inputStream.close();
		ftpClient.logout();
		*/
		
		FileInputStream inputStream = new FileInputStream(new File("D:\\Freedom\\2.jpg"));
		FtpUtil.uploadFile("192.168.11.140", 21, "ftpuser", "123456", "/home/ftpuser/www/images", "/2017/04/23", "2.jpg", inputStream);
	}
}
