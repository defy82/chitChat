package com.chitChat.resources;

import com.chitChat.api.User;
import com.chitChat.core.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api("/")
@Path("/users")
@SwaggerDefinition()
public class UserResource {

    private final UserRepository repository;

    public UserResource (UserRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() { return repository.findAll(); }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User create(User user) { return repository.save(user); }
}
