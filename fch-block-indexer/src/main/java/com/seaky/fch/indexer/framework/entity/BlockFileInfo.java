package com.seaky.fch.indexer.framework.entity;

public class BlockFileInfo {

    private long fileNum;

    private String filePath;

    private String basePath;

    private long offset=0;

    public BlockFileInfo(String basePath,long fileNum) {
        this(basePath,fileNum,0);
    }
    public BlockFileInfo(String basePath,long fileNum,long offset) {
        this.fileNum = fileNum;
        this.basePath=basePath;
        this.filePath=basePath+"/blocks/"+getFileName();
        this.offset=offset;
    }




    public String getFileName() {

        if (getFileNum() <= 99999)
            return String.format("blk%05d.dat", getFileNum());
        else if (getFileNum() <= 999999)
            return String.format("blk%06d.dat", getFileNum());
        else
            throw new RuntimeException("not support");
    }


    public long getOffset() {
        return offset;
    }

    public void addOffset(long offset) {
        this.offset += offset;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getFileNum() {
        return fileNum;
    }
}
