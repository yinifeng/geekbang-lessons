package org.geektimes.cache.serializer;

public interface RedisSerializer<T> {

    byte[] serialize(T t) throws RuntimeException;

    T deserialize(byte[] bytes) throws RuntimeException;
}
