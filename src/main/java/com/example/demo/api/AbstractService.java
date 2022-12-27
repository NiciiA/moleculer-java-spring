package com.example.demo.api;

import com.example.demo.domain.User;
import com.example.demo.services.RequestParser;
import io.datatree.Tree;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import services.moleculer.context.Context;
import services.moleculer.error.MoleculerErrorUtils;
import services.moleculer.service.Service;

import java.util.Locale;

public abstract class AbstractService extends Service {

    protected RequestParser requestParser;

    public AbstractService(RequestParser requestParser) {
        this.requestParser = requestParser;
    }

    public <T> Object parse(Context context, Class<T> c, ParsedAction<ActionRequest<T>, Object> f) throws Exception {
        Meta meta = Meta.of(context.params.getMeta(true));
        T body;

        try {
            body = requestParser.parse(c, context.params);
        } catch (ConstraintViolationException e) {
            Tree response = new Tree();
            response.put("name", "ValidationError")
                    .put("message", e.getMessage());
            throw MoleculerErrorUtils.create(response);
        }

        ActionRequest<T> request = new ActionRequest<>(body, context, meta);

        return f.apply(request);
    }

    public Object parse(Context context, ParsedAction<ActionRequest<?>, Object> f) throws Exception {
        Meta meta = Meta.of(context.params.getMeta(true));

        ActionRequest<?> request = new ActionRequest<>(null, context, meta);

        return f.apply(request);
    }

    @FunctionalInterface
    public interface ParsedAction<ActionRequest, R> {
        R apply(ActionRequest t) throws Exception;
    }

    protected record ActionRequest<T>(T body, @NotNull Context context, @NotNull Meta meta) {

    }

    protected static class Meta {

        @NotNull
        private Locale locale;

        private User user;

        public Meta() {
            locale = Locale.getDefault();
        }

        public static Meta of(Tree params) {
            Tree locale = params.get("locale");
            String language = locale.get("language", "de");
            String country = locale.get("country", "ch");

            Meta meta = new Meta();
            meta.setLocale(Locale.of(language, country));
            meta.setUser(new User());

            return meta;
        }

        @NotNull
        public Locale getLocale() {
            return locale;
        }

        public void setLocale(@NotNull Locale locale) {
            this.locale = locale;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

}
