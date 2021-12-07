package cn.uploadSys.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author dh
 * @version 1.0
 * @description: TODO
 * @date 2021/12/6 下午5:06
 */
@Slf4j
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> template;

    public void setRedis(String key,String value,long time) {
        if (StringUtils.isBlank(key)) {
            System.out.println( false);
        }
        try {
            template.opsForValue().set(key, value,time, TimeUnit.SECONDS);
            log.info("存入redis成功，key：{}，value：{}", key, value);
        } catch (Exception e) {
            log.error("存入redis失败，key：{}，value：{}", key, value);
            e.printStackTrace();
        }
    }

    public Object getRedis(String key){
        return template.opsForValue().get(key);
    }
}
