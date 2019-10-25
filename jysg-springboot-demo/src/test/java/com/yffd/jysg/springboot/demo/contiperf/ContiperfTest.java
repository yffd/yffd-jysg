package com.yffd.jysg.springboot.demo.contiperf;


import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ContiperfTest {

    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    /**
     * 性能测试
     * 10万次查询，100个线程同时操作findAll方法
     */
    @Test
    @PerfTest(invocations = 100000, threads = 100)
    public void contextLoads() {
        System.out.println("mmm");
    }

}
