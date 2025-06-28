package com.commit.collaboration_board_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@PropertySource("classpath:/secrets.properties")
@SpringBootApplication
//@EnableScheduling 스케쥴러 사용을 위한 어노테이션
@MapperScan("com.commit.collaboration_board_server.mapper") // Mapper 인터페이스가 위치한 패키지
public class CollaborationBoardServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborationBoardServerApplication.class, args);
	}

}
