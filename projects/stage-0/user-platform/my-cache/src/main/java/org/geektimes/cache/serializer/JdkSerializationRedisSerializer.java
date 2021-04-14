package org.geektimes.cache.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JdkSerializationRedisSerializer implements RedisSerializer<Object> {
    
    @Override
    public byte[] serialize(Object o) throws RuntimeException {
        byte[] bytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            // Key -> byte[]
            objectOutputStream.writeObject(o);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes) throws RuntimeException {
        if (bytes == null) {
            return null;
        }
        Object value = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            // byte[] -> Value
            value = objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
