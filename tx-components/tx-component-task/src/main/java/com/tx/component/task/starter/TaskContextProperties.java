/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.task.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
  * 命令容器配置<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月1日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@ConfigurationProperties(prefix = "task")
public class TaskContextProperties {
    
    /** 命令容器是否启动 */
    private boolean enable;
    
    /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
    private String module;
    
    /** 基础包集合 */
    private String basePackages = "com.tx";
    
    /** mybatis配置文件 */
    private String mybatisConfigLocation = "classpath:context/mybatis-config.xml";
    
    /** 数据源 */
    private String dataSource;
    
    /** 事务管理器 */
    private String transactionManager;
    
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
     * @return 返回 dataSource
     */
    public String getDataSource() {
        return dataSource;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @return 返回 transactionManager
     */
    public String getTransactionManager() {
        return transactionManager;
    }
    
    /**
     * @param 对transactionManager进行赋值
     */
    public void setTransactionManager(String transactionManager) {
        this.transactionManager = transactionManager;
    }
    
}
