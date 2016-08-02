package br.com.emerson.repository;

import java.util.UUID;

public interface RedisRepository {

    UUID createSession(String user);

    String getSession(UUID uuid);

    void deleteSession(UUID uuid);
}
