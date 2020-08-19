package com.mingduo.security.core.validate.code.image;

import com.mingduo.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 *
 * 图片验证码处理器
 *
 * @since 2019/10/25
 * @author : weizc 
 */
@Slf4j
@Component("imageCodeValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    public void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        log.info("生成的图形验证码:{}",validateCode.getCode());
        ImageIO.write(validateCode.getImage(),"JPEG",request.getResponse().getOutputStream());
    }
}
