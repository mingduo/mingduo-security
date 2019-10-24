package com.mingduo.security.core.validate.code.image;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 *
 * 图片验证码
 * @since 2019/10/23
 * @author : weizc 
 */
@AllArgsConstructor
@Data
public class ImageCode {
    //生成的图形
    private BufferedImage image;
    //验证码
    private String code;

    private LocalDateTime expireTime;

    public boolean isExpire(){
       return LocalDateTime.now().isAfter(expireTime);
    }


    public ImageCode(BufferedImage image, String code,long expireSeconds) {
        this.image = image;
        this.code = code;
        this.expireTime=LocalDateTime.now().plusSeconds(expireSeconds);
    }
}
