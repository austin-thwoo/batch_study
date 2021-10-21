package com.laonstory.poc_be_batch.job;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration//Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용합니다.
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public SimpleJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job SimpleJob(){//simpleJob 이란 이름의 Batch Job을 생성합니다. job의 이름은 별도로 지정하지 않고, 이렇게 Builder를 통해 지정합니다.
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1(null))
                .next(simpleStep2(null))
                .build();//simpleStep1 이란 이름의 Batch Step을 생성합니다. jobBuilderFactory.get("simpleJob")와 마찬가지로 Builder를 통해 이름을 지정합니다.
    }

    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    //Step 안에서 수행될 기능들을 명시합니다. Tasklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언할때 사용합니다.
                    // 여기서는 Batch가 수행되면 log.info(">>>>> This is Step1") 가 출력되도록 합니다.
//                    log.info(">>>>>This is Step 1");
//                    log.info(">>>>>requestDate '{}'",requestDate);
                    throw new IllegalArgumentException("step1에서 실패합니다.");
//                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step2");
                    log.info(">>>>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
