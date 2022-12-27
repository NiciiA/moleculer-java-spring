package com.example.demo.services;

import com.example.demo.domain.User;
import io.datatree.Tree;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.moleculer.context.Context;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

@Service
public class RequestParser {

    private final Validator validator;

    public RequestParser(Validator validator) {
        this.validator = validator;
    }

    public <T> T parse(Class<T> c, Tree params) throws Exception {
        T instance = c.getDeclaredConstructor().newInstance();
        PropertyAccessor accessor = PropertyAccessorFactory.forDirectFieldAccess(instance);

        Arrays.stream(c.getDeclaredFields()).forEach(field -> {
            if (field.getType().equals(Integer.TYPE)) {
                accessor.setPropertyValue(field.getName(), params.get(field.getName(), 0));
            } else if (field.getType().equals(String.class)) {
                accessor.setPropertyValue(field.getName(), params.get(field.getName(), (String) null));
            } else {
                // TODO: Property is of nested Obejct
            }
        });

        Set<ConstraintViolation<T>> violations = validator.validate(instance);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

        return instance;
    }

}
