package com.seaky.fch.indexer.framework.db.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("txinput_info")
public class TxInputDto {

    @TableId(value="id",type= IdType.AUTO)
    private long id;

    @TableField("tx_id")
    private long txId;

    @TableField("block_id")
    private long blockId;

    @TableField("tx_hash")
    private String txHash;

    @TableField("block_hash")
    private String blockHash;

    @TableField("script")
    private String script;

    @TableField("pre_tx_hash")
    private String preTxHash;

    @TableField("address")
    private String address;

    @TableField("pubkey")
    private String pubkey;

    @TableField("pre_tx_output_index")
    private long preTxOutputIndex;

    @TableField("pre_tx_output_id")
    private long preTxOutputId;

    @TableField("output_index")
    private long outputIndex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getBlockId() {
        return blockId;
    }

    public void setBlockId(long blockId) {
        this.blockId = blockId;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getPreTxHash() {
        return preTxHash;
    }

    public void setPreTxHash(String preTxHash) {
        this.preTxHash = preTxHash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public long getPreTxOutputIndex() {
        return preTxOutputIndex;
    }

    public void setPreTxOutputIndex(long preTxOutputIndex) {
        this.preTxOutputIndex = preTxOutputIndex;
    }

    public long getPreTxOutputId() {
        return preTxOutputId;
    }

    public void setPreTxOutputId(long preTxOutputId) {
        this.preTxOutputId = preTxOutputId;
    }



    public long getTxId() {
        return txId;
    }

    public void setTxId(long txId) {
        this.txId = txId;
    }

    public long getOutputIndex() {
        return outputIndex;
    }

    public void setOutputIndex(long outputIndex) {
        this.outputIndex = outputIndex;
    }
}
