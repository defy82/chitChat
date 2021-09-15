package com.chitChat.core;

import com.chitChat.api.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.base.Charsets;
import io.dropwizard.util.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class DefaultUserRepository implements UserRepository {

    private static final String DATA_SOURCE = "dummy_data.json";

    private List<User> userList;

    public DefaultUserRepository() {
        try {
            initData();
        } catch (IOException e) {
            throw new RuntimeException(DATA_SOURCE + "is missing or is unreadable");
        }
    }

    private void initData() throws IOException {
        URL url = Resources.getResource(DATA_SOURCE);
        String json = Resources.toString(url, Charsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        CollectionType type = mapper.getTypeFactory()
                .constructCollectionType(List.class, User.class);
        userList = mapper.readValue(json, type);
    }

    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userList.stream().filter(e -> e.getId() == id).findFirst();
    }

    @Override
    public User save (User user) {
        Optional<Long> maxId = userList.stream()
                .map(User::getId)
                .max(Long::compare);
        long nextId = maxId.map(x -> x + 1).orElse(1L);
        user.setId(nextId);
        userList.add(user);
        return user;
    }

}
