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
     * {
     * "ret":0,
     * "msg":"",
     * "nickname":"Peter",
     * "figureurl":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/30",
     * "figureurl_1":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/50",
     * "figureurl_2":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/100",
     * "figureurl_qq_1":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/40",
     * "figureurl_qq_2":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/100",
     * "gender":"男",
     * "is_yellow_vip":"1",
     * "vip":"1",
     * "yellow_vip_level":"7",
     * "level":"7",
     * "is_yellow_year_vip":"1"
     * }
     */
    /**
     * 	返回码
     */
    private String ret;
    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;
    /**
     *
     */
    private String openId;
    /**
     * 不知道什么东西，文档上没写，但是实际api返回里有。
     */
    private String is_lost;
    /**
     * 省(直辖市)
     */
    private String province;
    /**
     * 市(直辖市区)
     */
    private String city;
    /**
     * 出生年月
     */
    private String year;
    /**
     * 	用户在QQ空间的昵称。
     */
    private String nickname;
    /**
     * 	大小为30×30像素的QQ空间头像URL。
     */
    private String figureurl;
    /**
     * 	大小为50×50像素的QQ空间头像URL。
     */
    private String figureurl_1;
    /**
     * 	大小为100×100像素的QQ空间头像URL。
     */
    private String figureurl_2;
    /**
     * 	大小为40×40像素的QQ头像URL。
     */
    private String figureurl_qq_1;
    /**
     * 	大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
     */
    private String figureurl_qq_2;
    /**
     * 	性别。 如果获取不到则默认返回”男”
     */
    private String gender;
    /**
     * 	标识用户是否为黄钻用户（0：不是；1：是）。
     */
    private String is_yellow_vip;
    /**
     * 	标识用户是否为黄钻用户（0：不是；1：是）
     */
    private String vip;
    /**
     * 	黄钻等级
     */
    private String yellow_vip_level;
    /**
     * 	黄钻等级
     */
    private String level;
    /**
     * 标识是否为年费黄钻用户（0：不是； 1：是）
     */
    private String is_yellow_year_vip;
}
