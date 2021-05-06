package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SectionService")
public class SectionService {

	@Resource
	private SectionMapper sectionMapper;

	/**
	 * 查询列表
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		SectionExample sectionExample = new SectionExample();
		List<Section> sectionList = sectionMapper.selectByExample(sectionExample);

		PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
		pageDto.setTotal(pageInfo.getTotal());

		List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
		pageDto.setList(sectionDtoList);

	}

	/**
	 * 插入或保存
	 * @param sectionDto
	 */
	public void save(SectionDto sectionDto) {
		Section section = CopyUtil.copy(sectionDto, Section.class);
		if (StringUtils.isNullOrEmpty(sectionDto.getId())) {
			this.insert(section);
		} else {
			this.update(section);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		sectionMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param section
	 */
	private void insert(Section section) {
		section.setId(UuidUtil.getShortUuid());
		sectionMapper.insert(section);
	}

	/**
	 * 更新
	 *
	 * @param section
	 */
	private void update(Section section) {
		sectionMapper.updateByPrimaryKey(section);
	}
}
