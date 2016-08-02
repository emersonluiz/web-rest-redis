package br.com.emerson.repository;

import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;

public class DefaultRedisRepository implements RedisRepository {

    private RedisTemplate<UUID, String> redisTemplate;

    public DefaultRedisRepository(RedisTemplate<UUID, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UUID createSession(String user) {
        UUID token = UUID.randomUUID();
        redisTemplate.opsForValue().set(token, user);
        return token;
    }

    @Override
    public String getSession(UUID token) {
        String user = redisTemplate.opsForValue().get(token);
        return user;
    }

    @Override
    public void deleteSession(UUID uuid) {
        redisTemplate.delete(uuid);
    }

}
