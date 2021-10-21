package com.laonstory.poc_be_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing// 배치 기능 활성화
@SpringBootApplication
public class PocBeBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocBeBatchApplication.class, args);
    }

}
