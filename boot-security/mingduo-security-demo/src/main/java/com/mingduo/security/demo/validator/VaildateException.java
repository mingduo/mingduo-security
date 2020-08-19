package com.mingduo.security.demo.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 
 * @description:
 * @since 2019/10/18
 * @author : weizc 
 */
@AllArgsConstructor
@Data
public class VaildateException extends RuntimeException{

    private List<ObjectError>errors;
}
