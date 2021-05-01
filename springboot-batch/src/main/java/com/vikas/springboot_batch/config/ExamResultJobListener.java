package com.vikas.springboot_batch.config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class ExamResultJobListener implements JobExecutionListener{
	 
    private LocalDateTime startTime, stopTime;
 
    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = LocalDateTime.now();
        System.out.println("ExamResult Job starts at :"+startTime);
    }
 
    @Override
    public void afterJob(JobExecution jobExecution) {
        stopTime = LocalDateTime.now();
        System.out.println("ExamResult Job stops at :"+stopTime);
        System.out.println("Total time take in millis :"+getTimeInMillis(startTime , stopTime).toString());
 
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            System.out.println("ExamResult job completed successfully");
            //Here you can perform some other business logic like cleanup
        }else if(jobExecution.getStatus() == BatchStatus.FAILED){
            System.out.println("ExamResult job failed with following exceptions ");
            List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
            for(Throwable th : exceptionList){
                System.err.println("exception :" +th.getLocalizedMessage());
            }
        }
    }
 
    private Duration getTimeInMillis(LocalDateTime start, LocalDateTime stop){
        return Duration.between(stop, start);
    }
 
}
