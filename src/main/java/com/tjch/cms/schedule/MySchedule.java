package com.tjch.cms.schedule;


import com.tjch.cms.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Configuration
public class MySchedule {

	@Autowired
	private FileInfoService fileInfoService;
	
	@Scheduled(cron="20 0/30 * * * *")
	public  void  mytimer(){
		System.out.println("执行删除");
		try{
			fileInfoService.deleteValidFalse();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
