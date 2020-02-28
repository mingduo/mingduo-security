/**
 * 
 */
package com.mingduo.security.zuulgateway.domain;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author weizc
 *
 */
@Data
public class TokenInfo {

	private boolean active;
	
	private String client_id;
	
	private String[] scope;
	
	private String user_name;
	
	private String[] aud;
	
	private Date exp;
	
	private String[] authorities;


}
