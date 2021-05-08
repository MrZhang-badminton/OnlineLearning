package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ChapterService")
public class ChapterService {

	@Resource
	private ChapterMapper chapterMapper;

	/**
	 * 查询列表
	 * @param chapterPageDto
	 */
	public void list(ChapterPageDto chapterPageDto) {
		PageHelper.startPage(chapterPageDto.getPage(), chapterPageDto.getSize());
		ChapterExample chapterExample = new ChapterExample();

		ChapterExample.Criteria criteria = chapterExample.createCriteria();

		if(!StringUtils.isNullOrEmpty(chapterPageDto.getCourseId())){
			criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
		}

		List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);

		PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
		chapterPageDto.setTotal(pageInfo.getTotal());

		List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
		chapterPageDto.setList(chapterDtoList);

	}

	/**
	 * 插入或保存
	 * @param chapterDto
	 */
	public void save(ChapterDto chapterDto) {
		Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
		if (StringUtils.isNullOrEmpty(chapterDto.getId())) {
			this.insert(chapter);
		} else {
			this.update(chapter);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		chapterMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param chapter
	 */
	private void insert(Chapter chapter) {
		chapter.setId(UuidUtil.getShortUuid());
		chapterMapper.insert(chapter);
	}

	/**
	 * 更新
	 *
	 * @param chapter
	 */
	private void update(Chapter chapter) {
		chapterMapper.updateByPrimaryKey(chapter);
	}
}
