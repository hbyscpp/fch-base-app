package com.seaky.fch.indexer.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlockIndexerConfig {

    @Value("${block.basepath}")
    private String blockPath;

    public String getBlockPath() {
        return blockPath;
    }

    public void setBlockPath(String blockPath) {
        this.blockPath = blockPath;
    }
}
