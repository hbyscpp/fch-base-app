package com.seaky.fch.indexer.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.*;

@Service
public class FchScheduleTask {

    private ThreadPoolExecutor es = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(1000));

    @Autowired
    private BlockIndexer blockIndexer;

    @Autowired
    private FchDataRepareTask fchDataRepareTask;
    @PostConstruct
    public void runTask() {
        es.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    blockIndexer.parseBlock();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                   Thread.interrupted();
                   return;
                }
            }
        });
        es.submit(new Runnable() {
            @Override
            public void run() {
                fchDataRepareTask.repairHeight();
            }
        });
        es.submit(new Runnable() {
            @Override
            public void run() {
                fchDataRepareTask.repairUtxo();
            }
        });

        es.submit(new Runnable() {
            @Override
            public void run() {
                fchDataRepareTask.cleanDuplicateBlock();
            }
        });


    }
}
