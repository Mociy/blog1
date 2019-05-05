package com.example.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan(
        basePackages="com.example.*",
        sqlSessionFactoryRef = "sqlSessionFactory",
        annotationClass = Repository.class
)
@PropertySource(value = {"classpath:log.properties"})
@EnableCaching
public class BlogApplication {

    @Autowired
    private RedisTemplate redisTemplate=null;
    @PostConstruct
    public void init(){
        initRedisTemplate();
    }

    private void initRedisTemplate(){
        RedisSerializer stringSerializer=redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(redisTemplate.getValueSerializer());

    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
