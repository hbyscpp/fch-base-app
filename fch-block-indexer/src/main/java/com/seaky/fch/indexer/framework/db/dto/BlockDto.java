package com.seaky.fch.indexer.framework.db.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("block_info")
public class BlockDto {

    @TableId(value="id",type= IdType.AUTO)
    private long id;

    @TableField("version")
    private long version;

    @TableField("block_size")
    private long blockSize;
    @TableField("pre_block_hash")
    private String preBlockHash;

    @TableField("merkle_root_hash")
    private String merkleRootHash;

    @TableField("timestamp")
    private long timestamp;

    @TableField("target_difficulty")
    private long targetDifficulty;

    @TableField("block_hash")
    private String blockHash;

    @TableField("nonce")
    private long nonce;

    @TableField("tx_size")
    private int txSize;

    @TableField("height")
    private long height=-1;

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

    public long getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(long blockSize) {
        this.blockSize = blockSize;
    }

    public String getPreBlockHash() {
        return preBlockHash;
    }

    public void setPreBlockHash(String preBlockHash) {
        this.preBlockHash = preBlockHash;
    }

    public String getMerkleRootHash() {
        return merkleRootHash;
    }

    public void setMerkleRootHash(String merkleRootHash) {
        this.merkleRootHash = merkleRootHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTargetDifficulty() {
        return targetDifficulty;
    }

    public void setTargetDifficulty(long targetDifficulty) {
        this.targetDifficulty = targetDifficulty;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }


    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public int getTxSize() {
        return txSize;
    }

    public void setTxSize(int txSize) {
        this.txSize = txSize;
    }
}
