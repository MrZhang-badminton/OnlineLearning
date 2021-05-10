package com.course.file.controller.admin;

import com.course.server.domain.Test;
import com.course.server.dto.ResponseDto;
import com.course.server.service.TestService;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("/admin")
@RestController
public class UploadController {

	private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

	private static final String BUSINESS_NAME = "文件上传";

	@Resource
	private TestService testService;

	@RequestMapping("/upload")
	public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
		LOG.info("上传文件开始:{}");
		LOG.info(file.getOriginalFilename());
		LOG.info(String.valueOf(file.getSize()));

		// 保存文件到本地
		String fileName = file.getOriginalFilename();
		String key = UuidUtil.getShortUuid();
		String fullPath = "/Users/zhanghua/Desktop/OnlineLearning/file/teacher/" + key + "-" + fileName;
		File dest = new File(fullPath);
		file.transferTo(dest);
		LOG.info(dest.getAbsolutePath());

		ResponseDto<Object> responseDto = new ResponseDto<>();

		return responseDto;
	}
}
