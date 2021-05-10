package com.course.server.service;

import com.course.server.domain.File;
import com.course.server.domain.FileExample;
import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.FileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service("FileService")
public class FileService {

	@Resource
	private FileMapper fileMapper;

	/**
	 * 查询列表
	 * @param pageDto
	 */
	public void list(PageDto pageDto) {
		PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
		FileExample fileExample = new FileExample();
		List<File> fileList = fileMapper.selectByExample(fileExample);

		PageInfo<File> pageInfo = new PageInfo<>(fileList);
		pageDto.setTotal(pageInfo.getTotal());

		List<FileDto> fileDtoList = CopyUtil.copyList(fileList, FileDto.class);
		pageDto.setList(fileDtoList);

	}

	/**
	 * 插入或保存
	 * @param fileDto
	 */
	public void save(FileDto fileDto) {
		File file = CopyUtil.copy(fileDto, File.class);
		File fileDb = selectByKey(fileDto.getKey());
		if (fileDb == null) {
			this.insert(file);
		} else {
			fileDb.setShardIndex(fileDto.getShardIndex());
			this.update(fileDb);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		fileMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 插入
	 *
	 * @param file
	 */
	private void insert(File file) {
        Date now = new Date();
        file.setCreatedAt(now);
        file.setUpdatedAt(now);
		file.setId(UuidUtil.getShortUuid());
		fileMapper.insert(file);
	}

	/**
	 * 更新
	 *
	 * @param file
	 */
	private void update(File file) {
        file.setUpdatedAt(new Date());
		fileMapper.updateByPrimaryKey(file);
	}


	/**
	 * 根据key查询分片信息
	 * @param key
	 * @return
	 */
	public File selectByKey(String key) {
		FileExample example = new FileExample();
		example.createCriteria().andKeyEqualTo(key);
		List<File> fileList = fileMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(fileList)) {
			return null;
		} else {
			return fileList.get(0);
		}
	}
}
