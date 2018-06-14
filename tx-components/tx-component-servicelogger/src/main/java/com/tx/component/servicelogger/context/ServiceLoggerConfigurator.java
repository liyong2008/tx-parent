package com.tx.component.servicelogger.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 业务日志<br/>
 * 1、用以提供业务日志记录功能
 * 
 * @author brady
 * @version [版本号, 2012-12-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerConfigurator
        implements InitializingBean, ApplicationContextAware {
    
    /** 日志 */
    protected Logger logger = LoggerFactory
            .getLogger(ServiceLoggerContext.class);
    
    /** spring容器 */
    protected ApplicationContext applicationContext;
    
    /** */
    protected ServiceLoggerConfigurator() {
        super();
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("业务日志容器开始构建: ......");
        
        //进行容器构建
        doBuild();
        
        //初始化容器
        doInitContext();
        
        logger.info("业务日志容器构建完成: ......");
    }
    
    /**
     * 基础数据容器构建<br/>
     */
    protected void doBuild() throws Exception {
        
    }
    
    /**
     * 容器初始化<br/>
     */
    protected void doInitContext() throws Exception {
        
    }
}
