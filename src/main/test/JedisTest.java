import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: lusiwei
 * @Date: 2018/12/4 21:44
 * @Description:
 */
public class JedisTest {
//测试spring配置文件jedis
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-redis.xml");
        JedisPool jedisPool = applicationContext.getBean("jedisPool", JedisPool.class);
        Jedis resource = jedisPool.getResource();
        resource.set("xml", "spring xml test");
        System.out.println(resource.get("xml"));
        resource.close();
    }
}
