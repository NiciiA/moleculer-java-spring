package com.example.demo.middlewares;

import io.datatree.Tree;
import services.moleculer.service.Action;
import services.moleculer.service.Middleware;

public class AuthorizationMiddleware extends Middleware {

    @Override
    public Action install(Action action, Tree tree) {
        return context -> {
            localFunction();
            return action.handler(context);
        };
    }

    public void localFunction() {
        logger.info("local ffff");
    }
}
