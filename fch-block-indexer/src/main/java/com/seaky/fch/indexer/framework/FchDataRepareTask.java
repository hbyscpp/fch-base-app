package com.seaky.fch.indexer.framework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seaky.fch.indexer.framework.db.dto.BlockDto;
import com.seaky.fch.indexer.framework.db.dto.TransactionDto;
import com.seaky.fch.indexer.framework.db.dto.TxInputDto;
import com.seaky.fch.indexer.framework.db.dto.TxOutputDto;
import com.seaky.fch.indexer.framework.db.service.BlockDalService;
import com.seaky.fch.indexer.framework.db.service.TransactionDalService;
import com.seaky.fch.indexer.framework.db.service.TxInputDalService;
import com.seaky.fch.indexer.framework.db.service.TxOutputDalService;
import com.seaky.fch.indexer.framework.entity.TxOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class FchDataRepareTask {
    @Autowired
    private BlockDalService blockDalService;

    @Autowired
    private TxInputDalService txInputDalService;

    @Autowired
    private TxOutputDalService txOutputDalService;

    @Autowired
    private TransactionDalService transactionDalService;

    public void repairHeight() {

        QueryWrapper<BlockDto> wrapper = new QueryWrapper<>();
        wrapper.eq("height", -1);

        BlockDto dto = null;
        BlockDto tempdto = null;
        while (true) {

            if (tempdto == null) {
                dto = blockDalService.getOne(wrapper.orderByAsc("id").last("limit 0,1"));

            } else {
                dto = tempdto;
                tempdto = null;
            }
            if (dto == null) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    return;
                }
            } else {
                QueryWrapper<BlockDto> bwrapper = new QueryWrapper<>();
                bwrapper.eq("block_hash", dto.getPreBlockHash());
                BlockDto predto = blockDalService.getOne(bwrapper);
                if (predto != null && predto.getHeight() >= 0) {
                    dto.setHeight(predto.getHeight() + 1);
                    blockDalService.updateById(dto);
                } else {
                    tempdto = predto;
                }

            }
        }
    }


    public void repairUtxo() {

        QueryWrapper<TxInputDto> wrapper = new QueryWrapper<>();

        wrapper.eq("pre_tx_output_id", -1).ne("pre_tx_hash", "0000000000000000000000000000000000000000000000000000000000000000");

        TxInputDto dto = null;
        while (true) {

            dto = txInputDalService.getOne(wrapper.orderByAsc("id").last("limit 0,1"));
            if (dto == null) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    return;
                }
            } else {
                QueryWrapper<TxOutputDto> bwrapper = new QueryWrapper<>();
                bwrapper.eq("tx_hash", dto.getPreTxHash()).eq("output_index", dto.getPreTxOutputIndex());
                TxOutputDto predto = txOutputDalService.getOne(bwrapper);
                if (predto != null) {
                    dto.setPreTxOutputId(predto.getId());
                    predto.setUtxostatus(0);
                    updateTxInputAndOutPut(dto, predto);
                } else {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                        return;
                    }
                }

            }
        }


    }

    @Transactional
    public void updateTxInputAndOutPut(TxInputDto input, TxOutputDto output) {

        txOutputDalService.updateById(output);
        txInputDalService.updateById(input);

    }

    @Transactional
    public void deleteBlock(long blockid) {
        blockDalService.remove(new QueryWrapper<BlockDto>().eq("id",blockid));
        transactionDalService.remove(new QueryWrapper<TransactionDto>().eq("block_id",blockid));
        txInputDalService.remove(new QueryWrapper<TxInputDto>().eq("block_id",blockid));
        txOutputDalService.remove(new QueryWrapper<TxOutputDto>().eq("block_id",blockid));


    }

    public void cleanDuplicateBlock() {
        List<Map<String, Object>> results = blockDalService.getBaseMapper().getDuplicateBlock();

        if (results == null) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                return;
            }
        }
        long maxHeight = blockDalService.getBaseMapper().getMaxHeight();
        for (Map<String, Object> result : results) {
            int height = (int) result.get("height");

            QueryWrapper<BlockDto> blockQuery = new QueryWrapper<>();
            blockQuery.eq("height", height);
            List<BlockDto> dtos = blockDalService.list(blockQuery);
            for (BlockDto dto : dtos) {
                blockQuery = new QueryWrapper<>();
                blockQuery.eq("pre_block_hash", dto.getBlockHash());
                List<BlockDto> nextdtos = blockDalService.list(blockQuery);
                if ((nextdtos == null || nextdtos.size()==0) && dto.getHeight() < maxHeight) {
                    deleteBlock(dto.getId());
                }
            }
        }
    }
}
