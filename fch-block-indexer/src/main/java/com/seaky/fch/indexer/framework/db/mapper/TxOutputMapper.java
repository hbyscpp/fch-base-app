package com.seaky.fch.indexer.framework.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seaky.fch.indexer.framework.db.dto.TxOutputDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TxOutputMapper extends BaseMapper<TxOutputDto> {
}
