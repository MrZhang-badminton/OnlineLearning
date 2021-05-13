package com.course.server.service;

import com.course.server.domain.Resource;
import com.course.server.domain.ResourceExample;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ResourceMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ResourceService")
public class ResourceService {

	@javax.annotation.Resource
	private ResourceMapper resourceMapper;

	/**
	 * 查询列表
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		ResourceExample resourceExample = new ResourceExample();
		List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);

		PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
		pageDto.setTotal(pageInfo.getTotal());

		List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
		pageDto.setList(resourceDtoList);

	}

	/**
	 * 插入或保存
	 * @param resourceDto
	 */
	public void save(ResourceDto resourceDto) {
		Resource resource = CopyUtil.copy(resourceDto, Resource.class);
		if (StringUtils.isNullOrEmpty(resourceDto.getId())) {
			this.insert(resource);
		} else {
			this.update(resource);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		resourceMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param resource
	 */
	private void insert(Resource resource) {
		resource.setId(UuidUtil.getShortUuid());
		resourceMapper.insert(resource);
	}

	/**
	 * 更新
	 *
	 * @param resource
	 */
	private void update(Resource resource) {
		resourceMapper.updateByPrimaryKey(resource);
	}
}
