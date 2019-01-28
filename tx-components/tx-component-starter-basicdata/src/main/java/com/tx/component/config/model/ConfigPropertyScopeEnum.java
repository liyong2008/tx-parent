/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年2月23日
 * <修改描述:>
 */
package com.tx.component.config.model;

/**
 * 配置属性类型 <br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年2月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum ConfigPropertyScopeEnum {
    
    LOCAL("LOCAL", "本地配置项"),
    
    GLOBAL("GLOBAL", "全局配置项");
    
    private final String key;
    
    private final String name;
    
    /** <默认构造函数> */
    private ConfigPropertyScopeEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }
    
    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
}
