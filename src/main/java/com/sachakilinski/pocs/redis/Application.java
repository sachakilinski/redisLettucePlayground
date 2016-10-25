package com.sachakilinski.pocs.redis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {


        public static void main(String[] args) {
                AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
                RedisConnectionManager manager = ctx.getBean(RedisConnectionManager.class);
        }
}


