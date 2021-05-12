package com.course.server.service;

import com.course.server.domain.User;
import com.course.server.domain.UserExample;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.UserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service("UserService")
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Resource
	private UserMapper userMapper;

	/**
	 * 查询列表
	 *
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		UserExample userExample = new UserExample();
		List<User> userList = userMapper.selectByExample(userExample);

		PageInfo<User> pageInfo = new PageInfo<>(userList);
		pageDto.setTotal(pageInfo.getTotal());

		List<UserDto> userDtoList = CopyUtil.copyList(userList, UserDto.class);
		pageDto.setList(userDtoList);

	}

	/**
	 * 插入或保存
	 *
	 * @param userDto
	 */
	public void save(UserDto userDto) {
		User user = CopyUtil.copy(userDto, User.class);
		if (StringUtils.isNullOrEmpty(userDto.getId())) {
			this.insert(user);
		} else {
			this.update(user);
		}
	}

	/**
	 * 删除
	 *
	 * @param id
	 */
	public void delete(String id) {
		userMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param user
	 */
	private void insert(User user) {
		user.setId(UuidUtil.getShortUuid());
		User userDb = this.selectByLoginName(user.getLoginName());
		if(userDb != null){
			throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
		}
		userMapper.insert(user);
	}

	/**
	 * 更新
	 *
	 * @param user
	 */
	private void update(User user) {
		user.setPassword(null);
		userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 根据用户名查询用户信息
	 * @param loginName
	 * @return
	 */
	public User selectByLoginName(String loginName) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andLoginNameEqualTo(loginName);
		List<User> userList = userMapper.selectByExample(userExample);
		if (CollectionUtils.isEmpty(userList)) {
			return null;
		} else {
			return userList.get(0);
		}

	}


	/**
	 * 重置密码
	 * @param userDto
	 */
	public void savePassword(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setPassword(userDto.getPassword());
		userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 登录
	 * @param userDto
	 * @return
	 */
	public LoginUserDto login(UserDto userDto) {
		User user = selectByLoginName(userDto.getLoginName());
		if(user == null){
			LOG.info("用户名不存在,{}",user.getLoginName());
			throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
		}else {
			if(user.getPassword().equals(userDto.getPassword())){
				//登录成功
				return CopyUtil.copy(user,LoginUserDto.class);
			}else {
				LOG.info("密码不对，输入密码:{}，数据库密码:{}",userDto.getPassword(),user.getPassword());
				throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
			}
		}
	}

}