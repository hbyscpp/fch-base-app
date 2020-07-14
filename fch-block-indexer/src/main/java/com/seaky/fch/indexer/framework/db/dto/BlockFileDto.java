package com.seaky.fch.indexer.framework.db.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("block_file_info")
public class BlockFileDto {

    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @TableField("full_path")
    private String fullPath;
    @TableField("file_num")
    private Long fileNum;
    @TableField("offset")
    private Long offset;
    @TableField("base_path")
    private String basePath;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Long getFileNum() {
        return fileNum;
    }

    public void setFileNum(Long fileNum) {
        this.fileNum = fileNum;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
