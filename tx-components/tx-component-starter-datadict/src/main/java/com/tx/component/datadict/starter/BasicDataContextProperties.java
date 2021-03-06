/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.datadict.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = "tx.basicdata")
public class BasicDataContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** 基础包集合 */
    private String basePackages = "";
    
    /** cacheManager */
    private String cacheManagerRef;
    
    /** 服务端配置 */
    private BasicDataContextServerProperties server;
    
    /**
     * @return 返回 enable
     */
    public boolean isEnable() {
        return enable;
    }
    
    /**
     * @param 对enable进行赋值
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    /**
     * @return 返回 basePackages
     */
    public String getBasePackages() {
        return basePackages;
    }
    
    /**
     * @param 对basePackages进行赋值
     */
    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
    
    /**
     * @return 返回 cacheManagerRef
     */
    public String getCacheManagerRef() {
        return cacheManagerRef;
    }
    
    /**
     * @param 对cacheManagerRef进行赋值
     */
    public void setCacheManagerRef(String cacheManagerRef) {
        this.cacheManagerRef = cacheManagerRef;
    }
    
    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return 返回 server
     */
    public BasicDataContextServerProperties getServer() {
        return server;
    }

    /**
     * @param 对server进行赋值
     */
    public void setServer(BasicDataContextServerProperties server) {
        this.server = server;
    }
}
