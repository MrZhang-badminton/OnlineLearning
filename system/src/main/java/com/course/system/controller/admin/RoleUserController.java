package com.course.system.controller.admin;

import com.course.server.dto.RoleUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.RoleUserService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/roleUser")
public class RoleUserController {

	private static final Logger LOG = LoggerFactory.getLogger(RoleUserController.class);

	public static final String BUSINESS_NAME = "角色用户关联";

	@Resource
	private RoleUserService roleUserService;

	/**
	 * 列表查询
	 * @param pageDto
	 * @return
	 */
	@PostMapping("/list")
	public ResponseDto list(@RequestBody PageDto pageDto) {
		ResponseDto responseDto = new ResponseDto();
		roleUserService.list(pageDto);
		responseDto.setContent(pageDto);
		return responseDto;
	}

	/**
	 * 保存
	 * @param roleUserDto
	 * @return
	 */
	@PostMapping("/save")
	public ResponseDto save(@RequestBody RoleUserDto roleUserDto) {

		// 保存校验
		ValidatorUtil.require(roleUserDto.getRoleId(), "角色");
		ValidatorUtil.require(roleUserDto.getUserId(), "用户");

		ResponseDto responseDto = new ResponseDto();
		roleUserService.save(roleUserDto);
		responseDto.setContent(roleUserDto);
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
		roleUserService.delete(id);
		return responseDto;
	}
}
