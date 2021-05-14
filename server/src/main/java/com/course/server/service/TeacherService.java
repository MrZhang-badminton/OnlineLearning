package com.course.server.service;

import com.course.server.domain.Teacher;
import com.course.server.domain.TeacherExample;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.TeacherMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("TeacherService")
public class TeacherService {

	@Resource
	private TeacherMapper teacherMapper;

	/**
	 * 列表查询
	 */
	public List<TeacherDto> all() {
		TeacherExample teacherExample = new TeacherExample();
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		return CopyUtil.copyList(teacherList, TeacherDto.class);
	}

	/**
	 * 查询列表
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		TeacherExample teacherExample = new TeacherExample();
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);

		PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
		pageDto.setTotal(pageInfo.getTotal());

		List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
		pageDto.setList(teacherDtoList);

	}

	/**
	 * 插入或保存
	 * @param teacherDto
	 */
	public void save(TeacherDto teacherDto) {
		Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
		if (StringUtils.isNullOrEmpty(teacherDto.getId())) {
			this.insert(teacher);
		} else {
			this.update(teacher);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		teacherMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param teacher
	 */
	private void insert(Teacher teacher) {
		teacher.setId(UuidUtil.getShortUuid());
		teacherMapper.insert(teacher);
	}

	/**
	 * 更新
	 *
	 * @param teacher
	 */
	private void update(Teacher teacher) {
		teacherMapper.updateByPrimaryKey(teacher);
	}

	/**
	 * 查找
	 * @param id
	 */
	public TeacherDto findById(String id) {
		Teacher teacher = teacherMapper.selectByPrimaryKey(id);
		return CopyUtil.copy(teacher, TeacherDto.class);
	}
}
