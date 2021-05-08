package com.course.server.service;

import com.course.server.domain.Category;
import com.course.server.domain.CategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("CategoryService")
public class CategoryService {

	@Resource
	private CategoryMapper categoryMapper;

	/**
	 * 查询列表
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
		List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

		PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
		pageDto.setTotal(pageInfo.getTotal());

		List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
		pageDto.setList(categoryDtoList);

	}

	/**
	 * 插入或保存
	 * @param categoryDto
	 */
	public void save(CategoryDto categoryDto) {
		Category category = CopyUtil.copy(categoryDto, Category.class);
		if (StringUtils.isNullOrEmpty(categoryDto.getId())) {
			this.insert(category);
		} else {
			this.update(category);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		categoryMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param category
	 */
	private void insert(Category category) {
		category.setId(UuidUtil.getShortUuid());
		categoryMapper.insert(category);
	}

	/**
	 * 更新
	 *
	 * @param category
	 */
	private void update(Category category) {
		categoryMapper.updateByPrimaryKey(category);
	}
}
