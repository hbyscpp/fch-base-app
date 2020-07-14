package com.seaky.fch.indexer.framework.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seaky.fch.indexer.framework.db.dto.BlockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlockMapper extends BaseMapper<BlockDto> {

    @Select("SELECT height,num FROM (SELECT height,COUNT(height) AS num  FROM `block_info` GROUP BY height ) AS a WHERE a.num>1")
    public List<Map<String,Object>> getDuplicateBlock();

    @Select("SELECT max(height)   AS height  FROM `block_info`")
    public long getMaxHeight();

}
