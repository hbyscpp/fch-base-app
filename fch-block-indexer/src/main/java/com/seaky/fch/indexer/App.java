package com.seaky.fch.indexer;

import com.seaky.fch.indexer.framework.BlockIndexer;
import com.seaky.fch.indexer.framework.entity.Block;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.*;
import java.util.List;

/**
 * 索引程序,构建fch,bch,btc的索引
 */
@SpringBootApplication
@EnableScheduling
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(App.class, args);

    }
}
