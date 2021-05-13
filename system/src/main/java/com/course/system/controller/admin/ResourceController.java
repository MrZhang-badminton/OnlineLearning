package com.course.system.controller.admin;

import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ResourceService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

	public static final String BUSINESS_NAME = "资源";

	@Resource
	private ResourceService resourceService;

	/**
	 * 列表查询
	 * @param pageDto
	 * @return
	 */
	@PostMapping("/list")
	public ResponseDto list(@RequestBody PageDto pageDto) {
		ResponseDto responseDto = new ResponseDto();
		resourceService.list(pageDto);
		responseDto.setContent(pageDto);
		return responseDto;
	}

	/**
	 * 保存
	 * @param resourceDto
	 * @return
	 */
	@PostMapping("/save")
	public ResponseDto save(@RequestBody String jsonStr) {

		// 保存校验
		ValidatorUtil.require(jsonStr,"资源");
		ResponseDto responseDto = new ResponseDto();
		resourceService.saveJson(jsonStr);
		return responseDto;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseDto delete(@PathVariable String id) {
		ResponseDto responseDto = new ResponseDto();
		resourceService.delete(id);
		return responseDto;
	}
}
