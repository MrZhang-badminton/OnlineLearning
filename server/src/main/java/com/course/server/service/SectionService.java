package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service("SectionService")
public class SectionService {

	@Resource
	private SectionMapper sectionMapper;

	@Resource
	private CourseService courseService;

	/**
	 * 查询列表
	 *
	 * @param sectionPageDto
	 */
	public void list(SectionPageDto sectionPageDto) {
		PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
		SectionExample sectionExample = new SectionExample();
		SectionExample.Criteria criteria = sectionExample.createCriteria();
		if (!StringUtils.isNullOrEmpty(sectionPageDto.getCourseId())) {
			criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
		}
		if (!StringUtils.isNullOrEmpty(sectionPageDto.getChapterId())) {
			criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
		}
		sectionExample.setOrderByClause("sort asc");
		List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
		PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
		sectionPageDto.setTotal(pageInfo.getTotal());
		List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
		sectionPageDto.setList(sectionDtoList);
	}

	/**
	 * 插入或保存
	 *
	 * @param sectionDto
	 */
	@Transactional
	public void save(SectionDto sectionDto) {
		Section section = CopyUtil.copy(sectionDto, Section.class);
		if (StringUtils.isNullOrEmpty(sectionDto.getId())) {
			this.insert(section);
		} else {
			this.update(section);
		}
		courseService.updateTime(sectionDto.getCourseId());
	}

	/**
	 * 删除
	 *
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
		Date now = new Date();
		section.setCreatedAt(now);
		section.setUpdatedAt(now);
		section.setId(UuidUtil.getShortUuid());
		sectionMapper.insert(section);
	}

	/**
	 * 更新
	 *
	 * @param section
	 */
	private void update(Section section) {
		section.setUpdatedAt(new Date());
		sectionMapper.updateByPrimaryKey(section);
	}
}
