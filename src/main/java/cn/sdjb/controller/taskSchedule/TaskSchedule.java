package cn.sdjb.controller.taskSchedule;

import cn.sdjb.dao.ActionDao;
import cn.sdjb.entity.Action;
import cn.sdjb.entity.Email;
import cn.sdjb.service.EmailService;
import cn.sdjb.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TaskSchedule {
    @Autowired
    private ActionDao actionDao;
    @Autowired
    private Environment environment;
    @Autowired
    private EmailService emailService;
//    @Scheduled(cron="0/3 * *  * * ? ")
    @Scheduled(cron = "0 0 7 * * ?")
    public void sendMail()throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuffer sb = new StringBuffer();
        sb.append("where 1=1 ");
        sb.append("and real_close_time is null");
        List<Action> actionList = actionDao.selectActionList(sb.toString());

        for (Action action : actionList){
            Email email = emailService.getChosenEmailByAddress(action.getAddress());
            String content = "行动描述："+action.getDescriptive();
            content += "<br>";
            content += "关闭时间："+sdf.format(action.getCloseTime());
            switch (isNeedSendMail(action.getCloseTime())){
                case "W":
                    String[] emailArr = new String[1];
                    emailArr[0] = email.getEhsEmail();
                    MailUtil.sendEmail(email,"EHS", action.getEmail().split("\\|"), emailArr, content, null);
                    break;
                case "P":
                    String[] emailArr2 = new String[2];
                    emailArr2[0] = email.getEhsEmail();
                    emailArr2[1] = action.getDirectorEmail();
                    MailUtil.sendEmail(email,"EHS", action.getEmail().split("\\|"), emailArr2, content, null);
                    break;
                case "N":
                    break;
            }
            Thread.sleep(2000);
        }
    }
    public String isNeedSendMail(Date date){
        //完成时间前三天
        Calendar calendarAgo = Calendar.getInstance();
        calendarAgo.setTime(date);
        calendarAgo.add(Calendar.DATE,-3);
        Date dateAgo = calendarAgo.getTime();
        //完成时间后七天
        Calendar calendarAfter = Calendar.getInstance();
        calendarAfter.setTime(date);
        calendarAfter.add(Calendar.DATE,7);
        Date dateAfter = calendarAfter.getTime();
        //今天
        Date cdate = new Date();
        if(cdate.getTime()>dateAgo.getTime()&&cdate.getTime()<dateAfter.getTime()){
            //warn
            return "W";
        }else if(cdate.getTime()>dateAfter.getTime()){
            //punishment
            return "P";
        }else{
            return "N";
        }



    }
}
