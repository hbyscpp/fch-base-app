package com.seaky.fch.indexer.framework.entity;

import java.util.ArrayList;
import java.util.List;

public class Block {

    private long blockSize;

    private BlockHeader blockHeader;

    private List<Transaction> transactions = new ArrayList<>();

    public long getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(long blockSize) {
        this.blockSize = blockSize;
    }


    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public void addTransaction(Transaction trans) {
        this.transactions.add(trans);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isFirstBlock() {
        if ("00000000cbe04361b1d6de82b893a7d8419e76e99dd2073ac0db2ba0e652eea8".equals(blockHeader.getBlockHash())) {
            return true;
        } else {
            return false;
        }
    }
}
