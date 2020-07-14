package com.seaky.fch.indexer.framework.entity;

import com.seaky.fch.utils.ByteArrayUtils;
import com.seaky.fch.utils.HashUtils;

public class BlockHeader {

    private long version;

    private String preBlockHash;

    private String merkleRootHash;

    private long timestamp;

    private long targetDifficulty;

    private String blockHash;

    private long nonce;


    public BlockHeader(byte[] version, byte[] preBlockHash, byte[] merkleRootHash, byte[] timestamp, byte[] targetBits, byte[] nonce) {

        this.version = ByteArrayUtils.bytesToUnsignedIntLE(version);
        this.preBlockHash = ByteArrayUtils.bytesToHexReverse(preBlockHash);
        this.merkleRootHash = ByteArrayUtils.bytesToHexReverse(merkleRootHash);
        this.timestamp = ByteArrayUtils.bytesToUnsignedIntLE(timestamp);
        this.targetDifficulty = ByteArrayUtils.bytesToUnsignedIntLE(targetBits);
        this.nonce = ByteArrayUtils.bytesToUnsignedIntLE(nonce);
        this.blockHash=blockHash(version,preBlockHash,merkleRootHash,timestamp,targetBits,nonce);
    }

    private String blockHash(byte[] version, byte[] preBlockHash, byte[] merkleRootHash, byte[] timestamp, byte[] targetDifficulty, byte[] nonce) {

        String versionStr = ByteArrayUtils.bytesToHex(version);
        String preBlockHashStr = ByteArrayUtils.bytesToHex(preBlockHash);
        String merkleRootHashStr = ByteArrayUtils.bytesToHex(merkleRootHash);
        String timestampStr = ByteArrayUtils.bytesToHex(timestamp);
        String targetBitsStr = ByteArrayUtils.bytesToHex(targetDifficulty);
        String nonceStr = ByteArrayUtils.bytesToHex(nonce);
        String blockStr = versionStr + preBlockHashStr + merkleRootHashStr + timestampStr + targetBitsStr + nonceStr;
        byte[]  bytes=ByteArrayUtils.hexToBytes(blockStr);
        byte[] blockHash= HashUtils.sha256sha256(bytes);
        return ByteArrayUtils.bytesToHexReverse(blockHash);
    }

    public long getVersion() {
        return version;
    }

    public String getPreBlockHash() {
        return preBlockHash;
    }

    public String getMerkleRootHash() {
        return merkleRootHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getTargetDifficulty() {
        return targetDifficulty;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public long getNonce() {
        return nonce;
    }
}
