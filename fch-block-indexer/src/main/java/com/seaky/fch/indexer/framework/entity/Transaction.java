package com.seaky.fch.indexer.framework.entity;

import com.seaky.fch.utils.ByteArrayUtils;
import com.seaky.fch.utils.HashUtils;
import com.seaky.fch.utils.VarInt;

import java.util.List;

public class Transaction {

    private long version;

    //是否是coinbase,0是,1不是
    private int type;

    private List<TxInput> inputs;

    private List<TxOutput> outputs;

    private long locktime;

    private String txHash;

    public Transaction(long version, List<TxInput> inputs, List<TxOutput> outputs, long locktime)
    {
        this.version=version;
        this.inputs=inputs;
        this.outputs=outputs;
        this.locktime=locktime;
        this.txHash= HashUtils.sha256sha256HexReverse(encode());
    }
    public long getVersion() {
        return version;
    }

    public boolean isCoinBase() {
        if (inputs.size() == 1 && inputs.get(0).getPreTxHash().equals("0000000000000000000000000000000000000000000000000000000000000000")) {
            return true;
        } else {
            return false;
        }
    }

    public void setVersion(long version) {
        this.version = version;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<TxInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<TxInput> inputs) {
        this.inputs = inputs;
    }

    public List<TxOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<TxOutput> outputs) {
        this.outputs = outputs;
    }

    public long getLocktime() {
        return locktime;
    }

    public void setLocktime(long locktime) {
        this.locktime = locktime;
    }

    public byte[] encode() {
        byte[] versionBytes = new byte[4];
        ByteArrayUtils.uint32ToByteArrayLE(version, versionBytes, 0);
        VarInt inputLen = new VarInt(inputs.size());
        byte[] inputNumBytes = inputLen.encode();

        byte[] txbytes = ByteArrayUtils.concatBytes(versionBytes, inputNumBytes);


        for (int i = 0; i < inputs.size(); ++i) {
            TxInput input = inputs.get(i);
            txbytes = ByteArrayUtils.concatBytes(txbytes, input.encode());
        }
        VarInt outputLen = new VarInt(outputs.size());
        byte[] outputNumBytes = outputLen.encode();
        txbytes = ByteArrayUtils.concatBytes(txbytes, outputNumBytes);
        for (int i = 0; i < outputs.size(); ++i) {
            TxOutput output = outputs.get(i);
            txbytes = ByteArrayUtils.concatBytes(txbytes, output.encode());
        }

        byte[] locktimeBytes = new byte[4];
        ByteArrayUtils.uint32ToByteArrayLE(locktime, locktimeBytes, 0);
        txbytes = ByteArrayUtils.concatBytes(txbytes, locktimeBytes);
        return txbytes;
    }

    public String getTxHash() {
        return txHash;
    }

}
