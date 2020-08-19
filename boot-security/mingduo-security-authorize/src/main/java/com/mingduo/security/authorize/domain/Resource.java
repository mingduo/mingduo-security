package com.mingduo.security.authorize.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 需要控制权限的资源，以业务人员能看懂的name呈现.实际关联到一个或多个url上。
 *
 * 树形结构。
 *
 * @apiNode:
 * @since 2019/11/28
 * @author : weizc 
 */
@Data
public class Resource {

    private Long id;

    /**
     * 资源名称，如xx菜单，xx按钮
     */
    private String name;
    /**
     * 资源链接
     */
    private String link;
    /**
     * 图标
     */
    private String icon;
    /**
     * 资源类型
     */
    private ResourceType type;
    /**
     * 实际需要控制权限的url
     */
    private Set<String> urls;
    /**
     * 父资源
     */

    private Resource parent;
    /**
     * 子资源
     */

    private List<Resource> childs = new ArrayList<>();
}
