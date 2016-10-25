package com.sachakilinski.pocs.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.lambdaworks.redis.*;
import com.lambdaworks.redis.codec.Utf8StringCodec;
import com.lambdaworks.redis.masterslave.MasterSlave;
import com.lambdaworks.redis.masterslave.StatefulRedisMasterSlaveConnection;
import com.lambdaworks.redis.resource.ClientResources;
import com.lambdaworks.redis.resource.DefaultClientResources;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class reponsable to make inteface with Redis.
 *
 * @author Vinicius Araujo Geraldo (vinicius.geraldo@movile.com)
 */
@Component
public class RedisConnectionManager  {

        private static Logger log = LoggerFactory.getLogger(RedisConnectionManager.class);

        private final StatefulRedisMasterSlaveConnection<String, String> connection;

        @Autowired
        public RedisConnectionManager(RedisConfig config) {
                log.info("Starting Redis Cluster: {}.", config);

                RedisURI redisUri = getRedisUri(config);

                ClientResources clientRes = getClientResource(config);

                RedisClient client = RedisClient.create(clientRes, redisUri);

                connection = MasterSlave.connect(client, new Utf8StringCodec(), redisUri);
                connection.setReadFrom(ReadFrom.NEAREST);
                log.info("Connection MasterSlave created!");
        }

        private ClientResources getClientResource(RedisConfig config) {
                DefaultClientResources.Builder clientBuilder = new DefaultClientResources.Builder();

                if (config.getComputationThreadPoolSize() != null) {
                        clientBuilder.computationThreadPoolSize(config.getComputationThreadPoolSize());
                }

                if (config.getIoThreadPoolSize() != null) {
                        clientBuilder.ioThreadPoolSize(config.getIoThreadPoolSize());
                }

                return clientBuilder.build();
        }

        private RedisURI getRedisUri(RedisConfig config) {
                RedisURI.Builder builder = RedisURI.Builder.redis(config.getHost(), config.getPort());

                if (StringUtils.isNotEmpty(config.getPassword())) {
                        builder.withPassword(config.getPassword());
                }

                return builder.build();
        }

}