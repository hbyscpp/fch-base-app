package com.seaky.fch.indexer.framework.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seaky.fch.indexer.framework.db.dto.TxInputDto;
import com.seaky.fch.indexer.framework.db.dto.TxOutputDto;
import com.seaky.fch.indexer.framework.db.mapper.TxInputMapper;
import com.seaky.fch.indexer.framework.db.mapper.TxOutputMapper;
import org.springframework.stereotype.Service;

@Service
public class TxOutputDalService extends ServiceImpl<TxOutputMapper, TxOutputDto> {


}
