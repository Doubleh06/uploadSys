package cn.uploadSys.controller.taskSchedule;


import cn.uploadSys.service.upload.QczjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Slf4j
public class TaskSchedule {

    @Autowired
    private QczjService qczjService;

//    @Scheduled(cron="0/30 * *  * * ? ")//每10s执行一次
    @Scheduled(cron = "0 0 1 * * ?") //凌晨一点执行一次
    public void sendMail(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(new Date())+"执行~~~");
        qczjService.getUnfinishedInstance();

    }

}
