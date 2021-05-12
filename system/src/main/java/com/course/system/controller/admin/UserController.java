package com.course.system.controller.admin;

import com.course.server.dto.LoginUserDto;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.UserService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/admin/user")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	public static final String BUSINESS_NAME = "用户";

	@Resource
	private UserService userService;

	/**
	 * 列表查询
	 * @param pageDto
	 * @return
	 */
	@PostMapping("/list")
	public ResponseDto list(@RequestBody PageDto pageDto) {
		ResponseDto responseDto = new ResponseDto();
		userService.list(pageDto);
		responseDto.setContent(pageDto);
		return responseDto;
	}

	/**
	 * 保存
	 * @param userDto
	 * @return
	 */
	@PostMapping("/save")
	public ResponseDto save(@RequestBody UserDto userDto) {
		userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));

		// 保存校验
		ValidatorUtil.require(userDto.getLoginName(), "登陆名");
		ValidatorUtil.length(userDto.getLoginName(), "登陆名", 1, 50);
		ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
		ValidatorUtil.require(userDto.getPassword(), "密码");

		ResponseDto responseDto = new ResponseDto();
		userService.save(userDto);
		responseDto.setContent(userDto);
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
		userService.delete(id);
		return responseDto;
	}

	/**
	 * 重置密码
	 */
	@PostMapping("/save-password")
	public ResponseDto savePassword(@RequestBody UserDto userDto) {
		userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
		ResponseDto responseDto = new ResponseDto();
		userService.savePassword(userDto);
		responseDto.setContent(userDto);
		return responseDto;
	}

	/**
	 * 登录
	 */
	@PostMapping("/login")
	public ResponseDto login(@RequestBody UserDto userDto) {
		userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
		ResponseDto responseDto = new ResponseDto();
		LoginUserDto loginUserDto = userService.login(userDto);
		responseDto.setContent(loginUserDto);
		return responseDto;
	}
}
