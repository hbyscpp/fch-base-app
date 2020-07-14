package com.seaky.fch.indexer.framework.entity;

import com.seaky.fch.utils.ByteArrayUtils;
import com.seaky.fch.utils.FchMainNetParams;
import com.seaky.fch.utils.VarInt;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.script.Script;

public class TxOutput {

    private long satoshi;

    private long index;

    private String script;

    private String address;

    private String opreturn;

    private int scriptType;

    public TxOutput(byte[] satoshiBytes, byte[] scriptBytes, long index) {
        this.satoshi = ByteArrayUtils.bytesToLongLE(satoshiBytes);
        this.script = ByteArrayUtils.bytesToHex(scriptBytes);
        this.index = index;
        Script script = new Script(scriptBytes);
        if (script.isOpReturn()) {
            this.scriptType = 0;
            this.address = "";
            this.opreturn = ByteArrayUtils.bytesToHex(script.getChunks().get(1).data);
        } else {
            if ((script.getScriptType() == Script.ScriptType.P2SH) || (script.getScriptType() == Script.ScriptType.P2PKH)
                    || (script.getScriptType() == Script.ScriptType.P2PK)) {
                Address addrss = script.getToAddress(FchMainNetParams.get(), true);
                this.address = addrss.toString();
            }
            this.scriptType = script.getScriptType() == null ? -1 : script.getScriptType().id;
        }

    }

    public long getSatoshi() {
        return satoshi;
    }

    public void setSatoshi(long satoshi) {
        this.satoshi = satoshi;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public byte[] encode() {
        {
            byte[] outputBytes = null;
            byte[] satoshiBytes = new byte[8];
            ByteArrayUtils.int64ToByteArrayLE(satoshi, satoshiBytes, 0);
            byte[] scriptBytes = ByteArrayUtils.hexToBytes(script);
            VarInt scriptLen = new VarInt(scriptBytes.length);
            outputBytes = ByteArrayUtils.concatBytes(satoshiBytes, scriptLen.encode());
            outputBytes = ByteArrayUtils.concatBytes(outputBytes, scriptBytes);
            return outputBytes;
        }
    }

    public String getAddress() {
        return address;
    }

    public String getOpreturn() {
        return opreturn;
    }

    public int getScriptType() {
        return scriptType;
    }
}
