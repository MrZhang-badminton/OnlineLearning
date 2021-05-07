package com.course.business.controller.admin;

import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

	private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);

	public static final String BUSINESS_NAME = "课程";

	@Resource
	private CourseService courseService;

	/**
	 * 列表查询
	 *
	 * @param pageDto
	 * @return
	 */
	@PostMapping("/list")
	public ResponseDto list(@RequestBody PageDto pageDto) {
		ResponseDto responseDto = new ResponseDto();
		courseService.list(pageDto);
		responseDto.setContent(pageDto);
		return responseDto;
	}

	/**
	 * 保存
	 *
	 * @param courseDto
	 * @return
	 */
	@PostMapping("/save")
	public ResponseDto save(@RequestBody CourseDto courseDto) {

		// 保存校验


		ResponseDto responseDto = new ResponseDto();
		courseService.save(courseDto);
		responseDto.setContent(courseDto);
		return responseDto;
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseDto delete(@PathVariable String id) {
		ResponseDto responseDto = new ResponseDto();
		courseService.delete(id);
		return responseDto;
	}
}
