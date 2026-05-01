package com.ruoyi.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * 工作流模块启动类
 * 
 * @author ruoyi
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRyFeignClients
public class RuoYiFlowableApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiFlowableApplication.class, args);
    }
}