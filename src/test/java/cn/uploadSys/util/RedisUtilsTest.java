//package cn.uploadSys.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.concurrent.TimeUnit;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class RedisUtilsTest {
//    @Autowired
//    private RedisTemplate<String, Object> template;
//
//    @Test
//    public void setRedis() {
//        String key = "ultraman";
//        String value = "迪迦";
//        if (StringUtils.isBlank(key)) {
//            System.out.println( false);
//        }
//        try {
//            template.opsForValue().set(key, value,10, TimeUnit.SECONDS);
//            log.info("存入redis成功，key：{}，value：{}", key, value);
//            System.out.println( true);
//        } catch (Exception e) {
//            log.error("存入redis失败，key：{}，value：{}", key, value);
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getRedis(){
//        System.out.println(template.opsForValue().get("ultraman"));
//    }
//
//}