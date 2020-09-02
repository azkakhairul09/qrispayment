package com.sangbango.project.security;

import com.sangbango.project.SpringApplicationContext;

public class SecurityConstant {
	public static final long EXPIRATION_TIME = 1000*60*60*24;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String ROLE = "/setrole";
	public static final String SIGN_UP = "/registration";
	public static final String LOGIN = "/login";
	public static final String HELLOWORLD = "/";
	public static final String UPLOAD = "/upload";
	public static final String QREN_NOTIF = "/qris/paymentnotifqren";
	public static final String SOME_PRODUCTS = "/product/someproducts";
	public static final String PRODUCTS = "/product/all";
	public static final String PRODUCT_DETAIL = "/product/detail";

	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}
}
