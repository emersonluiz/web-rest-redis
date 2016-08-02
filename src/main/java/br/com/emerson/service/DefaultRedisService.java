package br.com.emerson.service;

import java.net.URI;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import br.com.emerson.model.User;
import br.com.emerson.repository.DefaultRedisRepository;

public class DefaultRedisService implements RestService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRedisService.class);

    @Autowired
    RedisTemplate<UUID, String> redisTemplate;

    @Override
    public Response createSession(UriInfo uriInfo, User user) {
        try {
            if (user == null) {
                logger.error("User is null");
                return Response.status(400).build();
            }
            if (user.getName() == null || user.getName().isEmpty()) {
                logger.error("User name is null.");
                return Response.status(400).build();
            }

            DefaultRedisRepository redisRepository = new DefaultRedisRepository(redisTemplate);
            UUID tk = redisRepository.createSession(user.getName());
            String token = tk.toString();
            URI uri = uriInfo.getRequestUriBuilder().path(token).build();
            logger.debug("Session created successfully.");
            return Response.created(uri).entity(token).build();
        } catch (Exception e) {
            logger.error("Error with create session");
            return Response.serverError().entity("Error with create session").build();
        }
    }

    @Override
    public Response getSession(String uuid) {
        try {
            DefaultRedisRepository redisRepository = new DefaultRedisRepository(redisTemplate);
            User user = new User();
            String name = redisRepository.getSession(UUID.fromString(uuid));
            if (name == null) {
                logger.error("User was not found.");
                return Response.status(404).build();
            }
            user.setName(name);
            logger.debug("Session was found successfully.");
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            logger.error("Error with get session");
            return Response.serverError().entity("Error with get session").build();
        }
    }

    @Override
    public Response deleteSession(String uuid) {
        try {
            DefaultRedisRepository redisRepository = new DefaultRedisRepository(redisTemplate);
            redisRepository.deleteSession(UUID.fromString(uuid));
            logger.debug("Session deleted successfully.");
            return Response.noContent().build();
        } catch (Exception e) {
            logger.error("Error with get session");
            return Response.serverError().entity("Error with get session").build();
        }
    }

}
