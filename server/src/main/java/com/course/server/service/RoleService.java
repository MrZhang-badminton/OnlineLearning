package com.course.server.service;

import com.course.server.domain.Role;
import com.course.server.domain.RoleExample;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("RoleService")
public class RoleService {

	@Resource
	private RoleMapper roleMapper;

	/**
	 * 查询列表
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		RoleExample roleExample = new RoleExample();
		List<Role> roleList = roleMapper.selectByExample(roleExample);

		PageInfo<Role> pageInfo = new PageInfo<>(roleList);
		pageDto.setTotal(pageInfo.getTotal());

		List<RoleDto> roleDtoList = CopyUtil.copyList(roleList, RoleDto.class);
		pageDto.setList(roleDtoList);

	}

	/**
	 * 插入或保存
	 * @param roleDto
	 */
	public void save(RoleDto roleDto) {
		Role role = CopyUtil.copy(roleDto, Role.class);
		if (StringUtils.isNullOrEmpty(roleDto.getId())) {
			this.insert(role);
		} else {
			this.update(role);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param role
	 */
	private void insert(Role role) {
		role.setId(UuidUtil.getShortUuid());
		roleMapper.insert(role);
	}

	/**
	 * 更新
	 *
	 * @param role
	 */
	private void update(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}
}
