package cn.sdjb.controller.taskSchedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class taskScheduleTest {

    @Autowired
    TaskSchedule taskSchedule;

    @Test
    public void sendMail() {
        try {
            taskSchedule.sendMail();
        }catch (Exception e){

        }

    }
}