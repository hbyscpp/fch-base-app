package com.seaky.fch.indexer.framework.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seaky.fch.indexer.framework.db.dto.TransactionDto;
import com.seaky.fch.indexer.framework.db.dto.TxInputDto;
import com.seaky.fch.indexer.framework.db.mapper.TransactionMapper;
import com.seaky.fch.indexer.framework.db.mapper.TxInputMapper;
import org.springframework.stereotype.Service;

@Service
public class TxInputDalService extends ServiceImpl<TxInputMapper, TxInputDto> {
}
