package com.seaky.fch.indexer.framework;

import com.seaky.fch.indexer.framework.config.BlockIndexerConfig;
import com.seaky.fch.indexer.framework.entity.*;
import com.seaky.fch.utils.ByteArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockIndexer {

    @Autowired
    private BlockIndexerConfig blockIndexerConfig;

    @Autowired
    private BlockStorge blockStorge;

    /**
     * 目前是单线程解析区块
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    //@PostConstruct
    public void parseBlock() throws IOException, InterruptedException {
        //1,找到路径
        BlockFileInfo fileInfo = new BlockFileInfo(blockIndexerConfig.getBlockPath(), 0);
        long fileNum = 0;
        while (true) {
            fileInfo = blockStorge.findIndexFileParseOffset(fileInfo.getFilePath());
            if (fileInfo == null) {
                fileInfo = new BlockFileInfo(blockIndexerConfig.getBlockPath(), fileNum);
            }
            parseFile(fileInfo);
            fileInfo = nextFile(fileInfo);
            fileNum = fileInfo.getFileNum();
        }

    }


    private BlockFileInfo nextFile(BlockFileInfo curFileInfo) {
        return new BlockFileInfo(curFileInfo.getBasePath(), curFileInfo.getFileNum() + 1);
    }


    private void parseFile(BlockFileInfo fileInfo) throws IOException, InterruptedException {

        File currentFile = new File(fileInfo.getFilePath());
        File nextFile = new File(nextFile(fileInfo).getFilePath());
        while (true) {
            if (!currentFile.exists()) {
                //TODO 日志，等待10s
                Thread.sleep(10000);
            } else {
                break;
            }
        }
        if (currentFile.length() <= fileInfo.getOffset()) {
            return;
        }
        try (RandomAccessFile fs = new RandomAccessFile(currentFile, "r")) {

            fs.seek(fileInfo.getOffset());
            long totalbytes = fileInfo.getOffset();

            while (true) {
                if ((totalbytes) <currentFile.length()) {

                    Block block = parseBlock(fs);
                    totalbytes += (block.getBlockSize() + 8);
                    fileInfo.addOffset(block.getBlockSize() + 8);
                    blockStorge.storge(block, fileInfo);
                }
                if (nextFile.exists()) {
                    if (totalbytes >= currentFile.length()) {
                        break;
                    }
                } else {
                    if (totalbytes  >= currentFile.length()) {
                        Thread.sleep(10000);
                    }
                }
            }
        }
    }


    //解析一个区块
    private Block parseBlock(RandomAccessFile fs) throws IOException, InterruptedException {
        Block b = new Block();
        byte[] magicbytes = readFull(fs, 4);
        String magicString = ByteArrayUtils.bytesToHex(magicbytes);
        while (!magicString.equals("f9beb4d9")) {
            fs.seek(fs.getFilePointer() - 4);
            Thread.sleep(10000);
            magicbytes= readFull(fs, 4);
            magicString=ByteArrayUtils.bytesToHex(magicbytes);
            //throw new RuntimeException(magicString + " block magic is not fch");
        }
        byte[] lenbytes = readFull(fs, 4);

        long blockSize = ByteArrayUtils.bytesToUnsignedIntLE(lenbytes);
        b.setBlockSize(blockSize);

        byte[] blockVersionBytes = readFull(fs, 4);

        byte[] preBlockHashBytes = readFull(fs, 32);

        byte[] merkleRootHashBytes = readFull(fs, 32);

        byte[] timestampHashBytes = readFull(fs, 4);

        byte[] targetBitsBytes = readFull(fs, 4);

        byte[] nonceBytes = readFull(fs, 4);

        BlockHeader header = new BlockHeader(blockVersionBytes, preBlockHashBytes, merkleRootHashBytes, timestampHashBytes, targetBitsBytes, nonceBytes);

        b.setBlockHeader(header);

        long transsize = readVarint(fs);
        for (long i = 0; i < transsize; ++i) {
            Transaction tx = parseTransaction(fs);
            b.addTransaction(tx);
        }

        return b;
    }

    //解析交易
    private Transaction parseTransaction(RandomAccessFile bs) throws IOException, InterruptedException {

        //byte[] txBytes=ByteArrayUtils.hexToBytes("02000000018370d1384b605fae1ab4b5000639611e5d598314a136610f842a338184f67bb7000000006441a4bfb5855fb4a6e21f61f85283df4598199338e5e8fa52b62f908f8b6f6e058f7269164e7ebc960faf6d26b02154d6fbbc93d3454a456e0b6e71dcf42638dec54121023219f536f6a98f4e599e4caad4b2197b6ce1cdba8570470fc22fb52783f3ae81ffffffff0340420f00000000001976a91467ece4efed6da877fcae9ce3eadcdd6760aa3edf88ac0000000000000000126a10464549507c337c317c737065616b7c7cca9de605000000001976a91444c337a31fdc97e1b2a885c00a0973c22cab423888ac00000000");

        //bs=new BufferedInputStream(new ByteArrayInputStream(txBytes));
        byte[] versionBytes = readFull(bs, 4);

        long version = ByteArrayUtils.bytesToUnsignedIntLE(versionBytes);

        long inputNum = readVarint(bs);
        List<TxInput> inputs = new ArrayList<>();
        for (long i = 0; i < inputNum; ++i) {
            TxInput input = parseTxInput(bs, i);
            input.setIndex(i);
            inputs.add(input);
        }
        long outputNum = readVarint(bs);
        List<TxOutput> outputs = new ArrayList<>();

        for (long i = 0; i < outputNum; ++i) {
            TxOutput output = parseTxOutput(bs, i);
            outputs.add(output);
        }
        byte[] locktimeBytes = readFull(bs, 4);
        long locktime = ByteArrayUtils.bytesToUnsignedIntLE(locktimeBytes);
        Transaction tx = new Transaction(version, inputs, outputs, locktime);
        return tx;

    }

    private TxInput parseTxInput(RandomAccessFile bs, long index) throws IOException, InterruptedException {


        byte[] preTxHashBytes = readFull(bs, 32);
        byte[] preTxOutIndex = readFull(bs, 4);
        long scriptLen = readVarint(bs);
        byte[] scriptBytes = readFull(bs, (int) scriptLen);
        byte[] seqBytes = readFull(bs, 4);
        TxInput input = new TxInput(preTxHashBytes, preTxOutIndex, scriptBytes, seqBytes, index);

        return input;
    }

    private TxOutput parseTxOutput(RandomAccessFile bs, long index) throws IOException, InterruptedException {
        byte[] outputNum = readFull(bs, 8);
        long scriptLen = readVarint(bs);
        byte[] scriptBytes = readFull(bs, (int) scriptLen);
        TxOutput output = new TxOutput(outputNum, scriptBytes, index);
        return output;
    }


    private long readVarint(RandomAccessFile bs) throws IOException {

        int firstByte = bs.read();
        if (firstByte <= 252)
            return firstByte;
        if (firstByte == 253) {
            byte[] bytes = new byte[2];
            bs.read(bytes);
            return ByteArrayUtils.bytesToShortLE(bytes);
        } else if (firstByte == 254) {
            byte[] bytes = new byte[4];
            bs.read(bytes);
            return ByteArrayUtils.bytesToUnsignedIntLE(bytes);
        } else if (firstByte == 255) {
            byte[] bytes = new byte[8];
            bs.read(bytes);
            return ByteArrayUtils.bytesToLongLE(bytes);
        } else {
            throw new RuntimeException("error transaction length first byte " + firstByte);
        }

    }


    private byte[] readFull(RandomAccessFile bs, int len) throws IOException, InterruptedException {
        byte[] buf = new byte[len];
        int totalReadNum = 0;
        int offset = 0;
        while (true) {
            int readbyteslen = bs.read(buf, totalReadNum, len - totalReadNum);

            if (readbyteslen < 0) {
                Thread.sleep(5000);
                continue;
            } else if (readbyteslen < (len - totalReadNum)) {
                totalReadNum += readbyteslen;

            } else {
                break;
            }
        }
        return buf;

    }


}
