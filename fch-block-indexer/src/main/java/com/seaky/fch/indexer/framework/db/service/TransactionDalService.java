package com.seaky.fch.indexer.framework.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seaky.fch.indexer.framework.db.dto.BlockDto;
import com.seaky.fch.indexer.framework.db.dto.TransactionDto;
import com.seaky.fch.indexer.framework.db.mapper.BlockMapper;
import com.seaky.fch.indexer.framework.db.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

@Service
public class TransactionDalService extends ServiceImpl<TransactionMapper, TransactionDto> {
}
