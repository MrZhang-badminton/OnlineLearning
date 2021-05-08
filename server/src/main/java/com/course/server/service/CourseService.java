package com.course.server.service;

import com.course.server.domain.Category;
import com.course.server.domain.Course;
import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service("CourseService")
public class CourseService {

	private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

	@Resource
	private CourseMapper courseMapper;

	@Resource
	private MyCourseMapper myCourseMapper;

	@Resource
	private CourseCategoryService courseCategoryService;

	/**
	 * 查询列表
	 *
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		CourseExample courseExample = new CourseExample();
		courseExample.setOrderByClause("sort asc");
		List<Course> courseList = courseMapper.selectByExample(courseExample);

		PageInfo<Course> pageInfo = new PageInfo<>(courseList);
		pageDto.setTotal(pageInfo.getTotal());

		List<CourseDto> courseDtoList = CopyUtil.copyList(courseList, CourseDto.class);
		pageDto.setList(courseDtoList);

	}

	/**
	 * 插入或保存
	 *
	 * @param courseDto
	 */
	public void save(CourseDto courseDto) {
		Course course = CopyUtil.copy(courseDto, Course.class);
		if (StringUtils.isNullOrEmpty(courseDto.getId())) {
			this.insert(course);
		} else {
			this.update(course);
		}

		//批量保存课程分类
		courseCategoryService.saveBatch(courseDto.getId(), courseDto.getCategorys());

	}

	/**
	 * 删除
	 *
	 * @param id
	 */
	public void delete(String id) {
		courseMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param course
	 */
	private void insert(Course course) {
		Date now = new Date();
		course.setCreatedAt(now);
		course.setUpdatedAt(now);
		course.setId(UuidUtil.getShortUuid());
		courseMapper.insert(course);
	}

	/**
	 * 更新
	 *
	 * @param course
	 */
	private void update(Course course) {
		course.setUpdatedAt(new Date());
		courseMapper.updateByPrimaryKey(course);
	}

	/**
	 * 更新课程时长
	 *
	 * @param courseId
	 */
	public void updateTime(String courseId) {
		LOG.info("更新课程时长：{}", courseId);
		myCourseMapper.updateTime(courseId);
	}

}
