package cn.uploadSys.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
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

    /**
     * @Author dhao
     * @Description 上锁
     * @Date 2023/3/7 
     * @Param  * @param key
     * @param value
     * @param time
     * @return java.lang.String
     **/
    public static String lock(String key,String value, int time) {
        Jedis jedis = null;
        try {
            jedis = new Jedis("localhost", 6379);
            if ("ok".equals(jedis.set(key, value, "NX", "EX", time))) {
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
    /**
     * @Author dhao
     * @Description 解锁
     * @Date 2023/3/7
     * @Param  * @param key
     * @param value
     * @return boolean
     **/
    public static boolean isLock(String key, String value) {
        if (key == null || value == null) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = new Jedis("localhost", 6379);
            String command = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            if (jedis.eval(command, Collections.singletonList(key),Collections.singletonList(value)).equals(1L)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public static void unLock(String key) {
        Jedis jedis = null;
        jedis = new Jedis("localhost", 6379);
        jedis.del(key);
    }

    /**
     * 获取唯一value
     *
     * @return
     */
    public static String fetchLockValue() {
        return UUID.randomUUID().toString() + "_" + System.currentTimeMillis();
    }
}
