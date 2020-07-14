package com.seaky.fch.indexer.framework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seaky.fch.indexer.framework.db.dto.*;
import com.seaky.fch.indexer.framework.db.mapper.*;
import com.seaky.fch.indexer.framework.db.service.*;
import com.seaky.fch.indexer.framework.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BlockStorge {

    @Autowired
    private BlockDalService blockDalService;


    @Autowired
    private TransactionDalService transactionDalService;

    @Autowired
    private TxInputDalService txInputDalService;

    @Autowired
    private TxOutputDalService txOutputDalService;

    @Autowired
    private BlockFileDalService blockFileDalService;


    public void storge(Block block, BlockFileInfo blockFileInfo) {

        //存储block信息
        QueryWrapper<BlockDto> wrapper = new QueryWrapper<>();
        wrapper.eq("block_hash", block.getBlockHeader().getBlockHash());

        BlockDto dto = blockDalService.getOne(wrapper);
        if (dto == null) {
            dto = new BlockDto();
            dto.setBlockHash(block.getBlockHeader().getBlockHash());
            dto.setBlockSize(block.getBlockSize());
            dto.setMerkleRootHash(block.getBlockHeader().getMerkleRootHash());
            dto.setNonce(block.getBlockHeader().getNonce());
            dto.setPreBlockHash(block.getBlockHeader().getPreBlockHash());
            dto.setTargetDifficulty(block.getBlockHeader().getTargetDifficulty());
            dto.setTimestamp(block.getBlockHeader().getTimestamp());
            dto.setTxSize(block.getTransactions().size());
            dto.setVersion(block.getBlockHeader().getVersion());
            if (block.isFirstBlock()) {
                dto.setHeight(0);
            } else {
                QueryWrapper<BlockDto> bwrapper = new QueryWrapper<>();
                bwrapper.eq("block_hash", block.getBlockHeader().getPreBlockHash());
                BlockDto predto = blockDalService.getOne(bwrapper);
                if (predto != null && predto.getHeight() >= 0) {
                    dto.setHeight(predto.getHeight() + 1);
                }
            }

            blockDalService.save(dto);
        }
        //存储交易信息

        List<Transaction> trans = block.getTransactions();
        int translen = trans.size();

        for (int i = 0; i < translen; ++i) {
            QueryWrapper<TransactionDto> transwrapper = new QueryWrapper<>();
            transwrapper.eq("tx_hash", trans.get(i).getTxHash());
            TransactionDto transdto = transactionDalService.getOne(transwrapper);
            if (transdto == null) {
                transdto = new TransactionDto();
                transdto.setBlockHash(dto.getBlockHash());
                transdto.setBlockId(dto.getId());
                transdto.setInputNum(trans.get(i).getInputs().size());
                transdto.setLocktime(trans.get(i).getLocktime());
                transdto.setOutputNum(trans.get(i).getOutputs().size());
                transdto.setType(trans.get(i).isCoinBase() ? 1 : 0);
                transdto.setTxHash(trans.get(i).getTxHash());
                transdto.setVersion(trans.get(i).getVersion());
                transactionDalService.save(transdto);
            }

            for (int j = 0; j < trans.get(i).getInputs().size(); ++j) {
                QueryWrapper<TxInputDto> inputwrapper = new QueryWrapper<>();
                inputwrapper.eq("tx_hash", trans.get(i).getTxHash()).eq("output_index", trans.get(i).getInputs().get(j).getIndex());
                TxInputDto inputdto = txInputDalService.getOne(inputwrapper);
                if (inputdto == null) {
                    TxInput input = trans.get(i).getInputs().get(j);
                    inputdto = new TxInputDto();
                    inputdto.setAddress(input.getAddress());
                    inputdto.setBlockHash(dto.getBlockHash());
                    inputdto.setBlockId(dto.getId());
                    inputdto.setOutputIndex(input.getIndex());
                    inputdto.setPreTxHash(input.getPreTxHash());
                    inputdto.setPreTxOutputIndex(input.getPreTxOutputIndex());
                    inputdto.setTxHash(trans.get(i).getTxHash());
                    inputdto.setTxId(transdto.getId());
                    inputdto.setScript(input.getScript());
                    inputdto.setPubkey(input.getPubkey());
                    //TODO查询pretxoutput
                    QueryWrapper<TxOutputDto> outputwrapper = new QueryWrapper<>();
                    outputwrapper.eq("tx_hash", inputdto.getPreTxHash()).eq("output_index",inputdto.getPreTxOutputIndex());
                    TxOutputDto outputdto = txOutputDalService.getOne(outputwrapper);
                    if (outputdto == null) {
                        inputdto.setPreTxOutputId(-1);
                    } else {
                        inputdto.setPreTxOutputId(outputdto.getId());
                        outputdto.setUtxostatus(0);
                        txOutputDalService.updateById(outputdto);

                    }
                    txInputDalService.save(inputdto);

                    //存储output


                }

            }


            for (int j = 0; j < trans.get(i).getOutputs().size(); ++j) {
                QueryWrapper<TxOutputDto> outputwrapper = new QueryWrapper<>();
                outputwrapper.eq("tx_hash", trans.get(i).getTxHash()).eq("output_index", trans.get(i).getOutputs().get(j).getIndex());
                TxOutputDto outputDto = txOutputDalService.getOne(outputwrapper);
                if (outputDto == null) {
                    TxOutput output = trans.get(i).getOutputs().get(j);
                    outputDto = new TxOutputDto();
                    outputDto.setAddress(output.getAddress());
                    outputDto.setBlockHash(dto.getBlockHash());
                    outputDto.setBlockId(dto.getId());
                    outputDto.setOutputIndex(output.getIndex());
                    outputDto.setOpreturn(output.getOpreturn());
                    outputDto.setSatoshi(output.getSatoshi());
                    outputDto.setScript(output.getScript());
                    outputDto.setScriptType(output.getScriptType());
                    outputDto.setTxhash(trans.get(i).getTxHash());
                    outputDto.setTxId(transdto.getId());
                    QueryWrapper<TxInputDto> iwrapper = new QueryWrapper<>();
                    iwrapper.eq("tx_hash", trans.get(i).getTxHash()).eq("output_index", trans.get(i).getOutputs().get(j).getIndex());
                    TxInputDto inputdto = txInputDalService.getOne(iwrapper);
                    if (inputdto != null) {
                        outputDto.setUtxostatus(0);
                    }
                    txOutputDalService.save(outputDto);
                }

            }

        }

        QueryWrapper<BlockFileDto> blockfilewrapper = new QueryWrapper<>();
        blockfilewrapper.eq("full_path",blockFileInfo.getFilePath());
        BlockFileDto blockfiledto = blockFileDalService.getOne(blockfilewrapper);
        if (blockfiledto == null) {
            blockfiledto = new BlockFileDto();
            blockfiledto.setFullPath(blockFileInfo.getFilePath());
            blockfiledto.setBasePath(blockFileInfo.getBasePath());
            blockfiledto.setFileNum(blockFileInfo.getFileNum());
            blockfiledto.setOffset(blockFileInfo.getOffset());
            blockFileDalService.save(blockfiledto);
        }
        else
        {
            blockfiledto.setOffset(blockFileInfo.getOffset());
            blockFileDalService.updateById(blockfiledto);
        }
    }


    //

    public BlockFileInfo findIndexFileParseOffset(String fullPath) {
        QueryWrapper<BlockFileDto> wrapper = new QueryWrapper<>();
        wrapper.eq("full_path", fullPath);
        BlockFileDto dto = blockFileDalService.getOne(wrapper);

        if (dto != null) {
            BlockFileInfo fileInfo = new BlockFileInfo(dto.getBasePath(), dto.getFileNum(), dto.getOffset());
            return fileInfo;
        }
        return null;
    }



}
