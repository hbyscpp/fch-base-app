package com.seaky.fch.indexer.framework.entity;

import com.seaky.fch.utils.ByteArrayUtils;
import com.seaky.fch.utils.FchMainNetParams;
import com.seaky.fch.utils.VarInt;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.script.Script;

public class TxInput {

    private String preTxHash;

    private long preTxOutputIndex;

    private long index;

    private String script;

    private long seq;

    private String address;

    private String pubkey;

    public TxInput(byte[] preTxHashBytes, byte[] preTxOutputIndexBytes, byte[] scriptBytes, byte[] seqBytes, long index) {
        this.preTxHash = ByteArrayUtils.bytesToHexReverse(preTxHashBytes);
        this.preTxOutputIndex = ByteArrayUtils.bytesToIntLE(preTxOutputIndexBytes);
        this.script = ByteArrayUtils.bytesToHex(scriptBytes);
        this.seq = ByteArrayUtils.bytesToUnsignedIntLE(seqBytes);

        if (isCoinBase()) {
            this.address = "";
            this.pubkey = "";
        } else {
            Script script = new Script(scriptBytes);
            byte[] pukkeybytes = script.getChunks().get(1).data;
            this.pubkey = ByteArrayUtils.bytesToHex(pukkeybytes);
            FchMainNetParams fchmainNetParams = FchMainNetParams.get();
            LegacyAddress inputaddress = LegacyAddress.fromKey(fchmainNetParams, ECKey.fromPublicOnly(pukkeybytes));
            this.address = inputaddress.toBase58();

        }


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

    public long getPreTxOutputIndex() {
        return preTxOutputIndex;
    }

    public void setPreTxOutputIndex(long preTxOutputIndex) {
        this.preTxOutputIndex = preTxOutputIndex;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public byte[] encode() {
        byte[] inputBytes = null;
        byte[] preTxHashBytes = ByteArrayUtils.hexToBytesReverse(preTxHash);
        byte[] preTxOutputIndexBytes = new byte[4];
        ByteArrayUtils.uint32ToByteArrayLE(preTxOutputIndex, preTxOutputIndexBytes, 0);
        byte[] scriptBytes = ByteArrayUtils.hexToBytes(script);
        VarInt scriptLen = new VarInt(scriptBytes.length);
        inputBytes = ByteArrayUtils.concatBytes(preTxHashBytes, preTxOutputIndexBytes);
        inputBytes = ByteArrayUtils.concatBytes(inputBytes, scriptLen.encode());
        inputBytes = ByteArrayUtils.concatBytes(inputBytes, scriptBytes);
        byte[] seqBytes = new byte[4];
        ByteArrayUtils.uint32ToByteArrayLE(seq, seqBytes, 0);
        inputBytes = ByteArrayUtils.concatBytes(inputBytes, seqBytes);
        return inputBytes;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public String getPubkey() {
        return pubkey;
    }

    public boolean isCoinBase() {
        return preTxHash.equals("0000000000000000000000000000000000000000000000000000000000000000");
    }
}
