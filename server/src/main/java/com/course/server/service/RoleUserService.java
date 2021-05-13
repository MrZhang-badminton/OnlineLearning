package com.course.server.service;

import com.course.server.domain.RoleUser;
import com.course.server.domain.RoleUserExample;
import com.course.server.dto.RoleUserDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleUserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("RoleUserService")
public class RoleUserService {

	@Resource
	private RoleUserMapper roleUserMapper;

	/**
	 * 查询列表
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		RoleUserExample roleUserExample = new RoleUserExample();
		List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);

		PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
		pageDto.setTotal(pageInfo.getTotal());

		List<RoleUserDto> roleUserDtoList = CopyUtil.copyList(roleUserList, RoleUserDto.class);
		pageDto.setList(roleUserDtoList);

	}

	/**
	 * 插入或保存
	 * @param roleUserDto
	 */
	public void save(RoleUserDto roleUserDto) {
		RoleUser roleUser = CopyUtil.copy(roleUserDto, RoleUser.class);
		if (StringUtils.isNullOrEmpty(roleUserDto.getId())) {
			this.insert(roleUser);
		} else {
			this.update(roleUser);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		roleUserMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param roleUser
	 */
	private void insert(RoleUser roleUser) {
		roleUser.setId(UuidUtil.getShortUuid());
		roleUserMapper.insert(roleUser);
	}

	/**
	 * 更新
	 *
	 * @param roleUser
	 */
	private void update(RoleUser roleUser) {
		roleUserMapper.updateByPrimaryKey(roleUser);
	}
}
