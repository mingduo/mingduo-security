package com.mingduo.security.core.validate.code.controller;

import com.mingduo.security.core.validate.code.image.ImageCode;
import com.mingduo.security.core.validate.code.image.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/23
 */
@RestController
public class ValidateCodeController {

    public static final String SEESSION_KEY = "SEESSION_KEY_IMAGE_CODE";

    @Autowired
    ValidateCodeGenerator imageGenerator;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = imageGenerator.generate(new ServletWebRequest(request));
        //设置session
        sessionStrategy.setAttribute(new ServletWebRequest(request), SEESSION_KEY, imageCode);

        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
