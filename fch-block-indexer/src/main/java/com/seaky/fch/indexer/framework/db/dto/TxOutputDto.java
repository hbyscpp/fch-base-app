package com.seaky.fch.indexer.framework.db.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("txoutput_info")
public class TxOutputDto {

    @TableId(value="id",type= IdType.AUTO)
    private long id;

    @TableField("tx_id")
    private long txId;

    @TableField("block_id")
    private long blockId;

    @TableField("tx_hash")
    private String txhash;

    @TableField("block_hash")
    private String blockHash;

    @TableField("satoshi")
    private long satoshi;

    @TableField("output_index")
    private long outputIndex;

    @TableField("script")
    private String script;

    //utxo
    @TableField("utxo_status")
    private int utxostatus=-1;

    @TableField("script_type")
    private int scriptType;

    //输出地址
    @TableField("address")
    private String address;

    //opreturn内容,hex编码
    @TableField("opreturn")
    private String opreturn;

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


    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public long getSatoshi() {
        return satoshi;
    }

    public void setSatoshi(long satoshi) {
        this.satoshi = satoshi;
    }



    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public int getUtxostatus() {
        return utxostatus;
    }

    public void setUtxostatus(int utxostatus) {
        this.utxostatus = utxostatus;
    }

    public int getScriptType() {
        return scriptType;
    }

    public void setScriptType(int scriptType) {
        this.scriptType = scriptType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpreturn() {
        return opreturn;
    }

    public void setOpreturn(String opreturn) {
        this.opreturn = opreturn;
    }

    public String getTxhash() {
        return txhash;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
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
