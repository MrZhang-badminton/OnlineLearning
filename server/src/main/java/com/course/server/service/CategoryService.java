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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("CategoryService")
public class CategoryService {

	@Resource
	private CategoryMapper categoryMapper;

	/**
	 * 查询列表
	 */
	public List<CategoryDto> all() {
		CategoryExample categoryExample = new CategoryExample();
		categoryExample.setOrderByClause("sort asc");
		List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
		List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
		return categoryDtoList;
	}

	/**
	 * 查询列表
	 *
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
	 *
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
	 * 删除一级分类
	 *
	 * @param id
	 */
	@Transactional
	public void delete(String id) {
		deleteChildren(id);
		categoryMapper.deleteByPrimaryKey(id);
	}

	public void deleteChildren(String id){
		Category category = categoryMapper.selectByPrimaryKey(id);
		if("000000000".equals(category.getParent())){
			// 如果是一级分类，需要删除其下的二级分类
			CategoryExample example = new CategoryExample();
			example.createCriteria().andParentEqualTo(category.getId());
			categoryMapper.deleteByExample(example);
		}
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
