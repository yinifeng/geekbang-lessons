package org.geektimes.cache.redis.lettuce;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;
import org.geektimes.cache.serializer.JdkSerializationRedisSerializer;
import org.geektimes.cache.serializer.RedisSerializer;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.util.Set;

public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {
    
    private final StatefulRedisConnection<byte[], byte[]> connection;
    
    private final RedisCommands<byte[], byte[]> redisCommands;

    private RedisSerializer<Object> redisSerializer = new JdkSerializationRedisSerializer();


    public LettuceCache(CacheManager cacheManager, String cacheName, 
                        Configuration<K, V> configuration,StatefulRedisConnection<byte[], byte[]> connection) {
        super(cacheManager, cacheName, configuration);
        this.connection = connection;
        this.redisCommands = connection.sync();
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = redisSerializer.serialize(key);
        return this.redisCommands.exists(keyBytes) > 0;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = redisSerializer.serialize(key);
        return getEntry(keyBytes);
    }

    protected ExpirableEntry<K, V> getEntry(byte[] keyBytes) throws CacheException, ClassCastException {
        byte[] valueBytes = this.redisCommands.get(keyBytes);
        return ExpirableEntry.of((K)redisSerializer.deserialize(keyBytes), (V)redisSerializer.deserialize(valueBytes));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        byte[] keyBytes = redisSerializer.serialize(entry.getKey());
        byte[] valueBytes = redisSerializer.serialize(entry.getValue());
        this.redisCommands.set(keyBytes, valueBytes);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = redisSerializer.serialize(key);
        ExpirableEntry<K, V> oldEntry = getEntry(keyBytes);
        this.redisCommands.del(keyBytes);
        return oldEntry;
    }

    @Override
    protected void clearEntries() throws CacheException {

    }

    @Override
    protected Set<K> keySet() {
        return null;
    }

    @Override
    protected void doClose() {
        this.connection.close();
    }
}
