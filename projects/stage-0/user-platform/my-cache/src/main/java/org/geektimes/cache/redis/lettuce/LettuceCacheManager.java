package org.geektimes.cache.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import org.geektimes.cache.AbstractCacheManager;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

public class LettuceCacheManager extends AbstractCacheManager {
    static final RedisCodec<byte[], byte[]> CODEC = ByteArrayCodec.INSTANCE;
    
    private RedisClient redisClient;
    
    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, 
                               Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        //this.uri =
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        this.redisClient = RedisClient.create(RedisURI.create(this.getURI()));
        StatefulRedisConnection<byte[], byte[]> connection = redisClient.connect(CODEC);
        return new LettuceCache(this,cacheName,configuration,connection);
    }

    @Override
    protected void doClose() {
        if (redisClient != null){
            redisClient.shutdown();
        }
    }
}
 