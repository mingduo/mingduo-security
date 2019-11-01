package com.mingduo.security.core.social.qq.api;

import lombok.Data;

/**
 *
 * 这个类的字段，是参照 qq互联开放平台 get_user_info这个接口的返回值来的
 * https://wiki.connect.qq.com/get_user_info
 * @since 2019/10/30
 * @author : weizc 
 */
@Data
public class QQUserInfo {


    /**
     * ret : 0
     * msg :
     * is_lost : 0
     * nickname : 打工之王
     * gender : 男
     * province : 江苏
     * city : 南京
     * year : 1991
     * constellation :
     * figureurl : http://qzapp.qlogo.cn/qzapp/101819538/662D101CB43B060AFDD85642EF1B2F37/30
     * figureurl_1 : http://qzapp.qlogo.cn/qzapp/101819538/662D101CB43B060AFDD85642EF1B2F37/50
     * figureurl_2 : http://qzapp.qlogo.cn/qzapp/101819538/662D101CB43B060AFDD85642EF1B2F37/100
     * figureurl_qq_1 : http://thirdqq.qlogo.cn/g?b=oidb&k=CL9ibibxNu1iaQTV1kOcdgCNg&s=40&t=1557056016
     * figureurl_qq_2 : http://thirdqq.qlogo.cn/g?b=oidb&k=CL9ibibxNu1iaQTV1kOcdgCNg&s=100&t=1557056016
     * figureurl_qq : http://thirdqq.qlogo.cn/g?b=oidb&k=CL9ibibxNu1iaQTV1kOcdgCNg&s=140&t=1557056016
     * figureurl_type : 1
     * is_yellow_vip : 0
     * vip : 0
     * yellow_vip_level : 0
     * level : 0
     * is_yellow_year_vip : 0
     */

    private int ret;
    private String msg;
    private String openId;
    private String is_lost;
    private String nickname;
    private String gender;
    private String province;
    private String city;
    private String year;
    private String constellation;
    private String figureurl;
    private String figureurl_1;
    private String figureurl_2;
    private String figureurl_qq_1;
    private String figureurl_qq_2;
    private String figureurl_qq;
    private String figureurl_type;
    private String is_yellow_vip;
    private String vip;
    private String yellow_vip_level;
    private String level;
    private String is_yellow_year_vip;










}

