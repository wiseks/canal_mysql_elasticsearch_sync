package com.star.sync.elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author <a href="mailto:wangchao.star@gmail.com">wangchao</a>
 * @version 1.0
 * @since 2017-08-25 17:26:00
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.star.sync.elasticsearch.dao")
public class CanalMysqlElasticsearchSyncApplication {
	
	private static String[] argsValue;
    public static void main(String[] args) {
        SpringApplication.run(CanalMysqlElasticsearchSyncApplication.class, args);
    }
    
    
    public void init() throws Exception {

		System.out.println("execute init method！");

	}

	public void init(String[] args) throws Exception {
		argsValue = args;
		System.out.println("execute init(args) method");

	}

	public void start() throws Exception {
		// 创建链接  
		SpringApplication.run(CanalMysqlElasticsearchSyncApplication.class, argsValue);
		System.out.println(">>>>>>>>>>>>server started！");

	}

	public void stop() throws Exception {
		
		System.out.println("execute stop method！");

	}

	public void destroy() throws Exception {

		System.out.println("execute destroy method!");

	}
}