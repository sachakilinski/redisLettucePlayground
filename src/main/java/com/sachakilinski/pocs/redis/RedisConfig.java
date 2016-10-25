package com.sachakilinski.pocs.redis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Created by sachakilinski on 10/25/16.
 */

@Component
public class RedisConfig {
        private Integer ioThreadPoolSize;
        private Integer computationThreadPoolSize;
        private String password;
        private String host;
        private int port;

        public Integer getIoThreadPoolSize() {
                return new Integer(2);
        }

        public Integer getComputationThreadPoolSize() {
                return new Integer(2);
        }


        public String getPassword() {
                return "";
        }


        public String getHost() {
                return "localhost";
        }

        public int getPort() {
                return 6379;
        }
}
