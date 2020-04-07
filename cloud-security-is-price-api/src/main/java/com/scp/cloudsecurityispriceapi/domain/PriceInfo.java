package com.scp.cloudsecurityispriceapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 
 * @apiNode:
 * @since 2020/4/3
 * @author : weizc 
 */
@AllArgsConstructor
@Data
public class PriceInfo {
    private BigDecimal price;
}
