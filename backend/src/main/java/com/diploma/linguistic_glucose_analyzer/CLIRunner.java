package com.diploma.linguistic_glucose_analyzer;

import com.diploma.linguistic_glucose_analyzer.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CLIRunner implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CLIRunner.class, args);
    }

    @Autowired
    private TestService testService;

    @Override
    public void run(String... args) throws Exception {
        int chainLength = 3;
        int bufferLength = 1;
        log.debug("Test for chain length = {} and buffer length = {}", chainLength, bufferLength);
        testService.test(chainLength, bufferLength);

//        chainLength = 6;
//        bufferLength = 1;
//        log.debug("Test for chain length = {} and buffer length = {}", chainLength, bufferLength);
//        testService.test(chainLength, bufferLength);

//        chainLength = 3;
//        bufferLength = 3;
//        log.debug("Test for chain length = {} and buffer length = {}", chainLength, bufferLength);
//        testService.test(chainLength, bufferLength);
//
//        chainLength = 3;
//        bufferLength = 3;
//        log.debug("Test for chain length = {} and buffer length = {}", chainLength, bufferLength);
//        testService.test(chainLength, bufferLength);

//        testService.testChain();
    }
}
