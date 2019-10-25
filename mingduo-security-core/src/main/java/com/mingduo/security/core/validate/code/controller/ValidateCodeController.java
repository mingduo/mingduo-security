package com.mingduo.security.core.validate.code.controller;

import com.mingduo.security.core.validate.code.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/23
 */
@RestController
public class ValidateCodeController {



    @Autowired
    Map<String, ValidateCodeProcessor> validateCodeProcessoros;

    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response,@PathVariable String type) throws Exception {

        validateCodeProcessoros.get(type+"CodeProcessor").create(new ServletWebRequest(request,response));


    }
}
