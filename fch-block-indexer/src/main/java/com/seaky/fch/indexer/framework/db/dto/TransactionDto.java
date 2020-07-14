package com.seaky.fch.indexer.framework.db.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("transaction_info")
public class TransactionDto {
    @TableId(value="id",type= IdType.AUTO)
    private long id;
    @TableField("version")
    private long version;
    @TableField("input_num")
    private long inputNum;

    @TableField("output_num")
    private long outputNum;

    //是否是coinbase,0是,1不是
    @TableField("type")
    private int type;

    @TableField("locktime")
    private long locktime;

    @TableField("tx_hash")
    private String txHash;

    @TableField("block_id")
    private long blockId;

    @TableField("block_hash")
    private String blockHash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getInputNum() {
        return inputNum;
    }

    public void setInputNum(long inputNum) {
        this.inputNum = inputNum;
    }

    public long getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(long outputNum) {
        this.outputNum = outputNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getLocktime() {
        return locktime;
    }

    public void setLocktime(long locktime) {
        this.locktime = locktime;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
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
}
