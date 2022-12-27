package com.example.demo.api;

import com.example.demo.dtos.AddMathDto;
import com.example.demo.services.RequestParser;
import io.datatree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import services.moleculer.context.Context;
import services.moleculer.service.*;

@Name("math")
@Controller
public class Math extends AbstractService {

    private MessageSource messageSource;

    @Autowired
    public Math(MessageSource messageSource, RequestParser requestParser) {
        super(requestParser);
        this.messageSource = messageSource;
    }

    @Auth
    @Name("add")
    @Params(AddMathDto.class)
    @Rest(method = "POST", path = "/add")
    public Action add = (Context ctx) -> parse(ctx, AddMathDto.class, (ActionRequest<AddMathDto> request) -> {
        AddMathDto body = request.body();
        Tree response = new Tree();

        logger.info(messageSource.getMessage("error.validation.min", null, request.meta().getLocale()));
        logger.info(request.meta().getLocale().toString());

        int c = body.getA() + body.getB();

        response.add(c);
        return response;
    });

    @Auth
    @Name("sub")
    @Rest(method = "POST", path = "/sub")
    public Action sub = (Context ctx) -> parse(ctx, (ActionRequest<?> request) -> {
        logger.info(request.meta().getLocale().toString());
        return new Tree();
    });

}
