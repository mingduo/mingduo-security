/**
 * 
 */
package security.cloud.security.product.api.controller;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author weizc
 *
 */
@Data
public class PriceInfo {
	
	private Long id;
	
	private BigDecimal price;

}
