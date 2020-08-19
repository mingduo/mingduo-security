/**
 * 
 */
package com.mingduo.security.demo.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * @author weizc
 * 在实现了QQ登录之后,自动帮用户注册,保存注册信息在数据库中
 */
//@Component
public class DemoConnectionSignUp implements ConnectionSignUp {


	@Override
	public String execute(Connection<?> connection) {
		//根据社交用户信息默认创建用户并返回用户唯一标识
		return connection.getDisplayName();
	}

}
