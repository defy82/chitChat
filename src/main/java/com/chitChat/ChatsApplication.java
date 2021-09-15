package com.chitChat;

import com.chitChat.api.User;
import com.chitChat.core.DefaultUserRepository;
import com.chitChat.core.UserRepository;
import com.chitChat.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ChatsApplication extends Application<ChatsConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ChatsApplication().run(args);
    }

    @Override
    public String getName() {
        return "Chats";
    }

    @Override
    public void initialize(final Bootstrap<ChatsConfiguration> bootstrap) {

        bootstrap.addBundle(new SwaggerBundle<ChatsConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration (ChatsConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final ChatsConfiguration configuration,
                    final Environment environment) {

        UserRepository repository = new DefaultUserRepository();
        UserResource userResource = new UserResource(repository);
        environment.jersey().register(userResource);
    }

}
