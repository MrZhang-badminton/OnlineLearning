package com.course.server.service;

import com.course.server.domain.RoleResource;
import com.course.server.domain.RoleResourceExample;
import com.course.server.dto.RoleResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("RoleResourceService")
public class RoleResourceService {

	@Resource
	private RoleResourceMapper roleResourceMapper;

	/**
	 * 查询列表
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		RoleResourceExample roleResourceExample = new RoleResourceExample();
		List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);

		PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResourceList);
		pageDto.setTotal(pageInfo.getTotal());

		List<RoleResourceDto> roleResourceDtoList = CopyUtil.copyList(roleResourceList, RoleResourceDto.class);
		pageDto.setList(roleResourceDtoList);

	}

	/**
	 * 插入或保存
	 * @param roleResourceDto
	 */
	public void save(RoleResourceDto roleResourceDto) {
		RoleResource roleResource = CopyUtil.copy(roleResourceDto, RoleResource.class);
		if (StringUtils.isNullOrEmpty(roleResourceDto.getId())) {
			this.insert(roleResource);
		} else {
			this.update(roleResource);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		roleResourceMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param roleResource
	 */
	private void insert(RoleResource roleResource) {
		roleResource.setId(UuidUtil.getShortUuid());
		roleResourceMapper.insert(roleResource);
	}

	/**
	 * 更新
	 *
	 * @param roleResource
	 */
	private void update(RoleResource roleResource) {
		roleResourceMapper.updateByPrimaryKey(roleResource);
	}
}
