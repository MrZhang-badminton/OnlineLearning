package com.course.server.service;

import com.course.server.domain.Role;
import com.course.server.domain.RoleExample;
import com.course.server.domain.RoleResource;
import com.course.server.domain.RoleResourceExample;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("RoleService")
public class RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private RoleResourceMapper roleResourceMapper;

//	@Resource
//	private RoleUserMapper roleUserMapper;

	/**
	 * 查询列表
	 *
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
	 *
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
	 *
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

	/**
	 * 按角色保存资源
	 */
	public void saveResource(RoleDto roleDto) {
		String roleId = roleDto.getId();
		List<String> resourceIds = roleDto.getResourceIds();
		// 清空库中所有的当前角色下的记录
		RoleResourceExample example = new RoleResourceExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		roleResourceMapper.deleteByExample(example);

		// 保存角色资源
		for (int i = 0; i < resourceIds.size(); i++) {
			RoleResource roleResource = new RoleResource();
			roleResource.setId(UuidUtil.getShortUuid());
			roleResource.setRoleId(roleId);
			roleResource.setResourceId(resourceIds.get(i));
			roleResourceMapper.insert(roleResource);
		}
	}

	/**
	 * 按角色加载资源
	 *
	 * @param roleId
	 */
	public List<String> listResource(String roleId) {
		RoleResourceExample example = new RoleResourceExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
		List<String> resourceIdList = new ArrayList<>();
		for (int i = 0, l = roleResourceList.size(); i < l; i++) {
			resourceIdList.add(roleResourceList.get(i).getResourceId());
		}
		return resourceIdList;
	}

}
