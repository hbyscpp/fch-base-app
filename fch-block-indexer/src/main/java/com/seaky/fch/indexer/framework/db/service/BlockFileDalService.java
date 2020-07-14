package com.seaky.fch.indexer.framework.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seaky.fch.indexer.framework.db.dto.BlockDto;
import com.seaky.fch.indexer.framework.db.dto.BlockFileDto;
import com.seaky.fch.indexer.framework.db.mapper.BlockFileMapper;
import com.seaky.fch.indexer.framework.db.mapper.BlockMapper;
import org.springframework.stereotype.Service;

@Service
public class BlockFileDalService extends ServiceImpl<BlockFileMapper, BlockFileDto> {
}
