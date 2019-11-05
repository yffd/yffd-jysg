package com.yffd.jysg.cache.redis.demo01.serializer;

import com.yffd.jysg.cache.redis.demo01.exception.SerializationException;

public interface RedisSerializer<T> {

    byte[] serialize(T t) throws SerializationException;

    T deserialize(byte[] bytes) throws SerializationException;
}
