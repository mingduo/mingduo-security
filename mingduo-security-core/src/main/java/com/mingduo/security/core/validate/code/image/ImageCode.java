package com.mingduo.security.core.validate.code.image;

import com.mingduo.security.core.validate.code.sms.ValidateCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

/**
 *
 * 图片验证码
 * @since 2019/10/23
 * @author : weizc 
 */
@Setter
@Getter
public class ImageCode extends ValidateCode {
    //生成的图形
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, long expireSeconds) {
        super(code,expireSeconds);
        this.image = image;

    }


}
