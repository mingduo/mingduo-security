package com.mingduo.security.core.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @description:
 * @since 2019/11/21
 * @author : weizc 
 */
@Data
public class OAuth2Properties {


    List<OAuth2ClientProperties> client=new ArrayList<>();
}
