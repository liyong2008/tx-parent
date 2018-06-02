/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-24
 * <修改描述:>
 */
package com.tx.component.servicelogger.context;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.servicelogger.service.LoggerService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务日志容器<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerContext extends ServiceLoggerFactory implements InitializingBean {
    
    private static ServiceLoggerContext context;
    
    protected ServiceLoggerContext() {
        super();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("初始化日志容器..");
        
        super.afterPropertiesSet();
        context = this;
        
        logger.info("初始化日志容器完成..");
    }
    
    public static ServiceLoggerContext getContext() {
        AssertUtils.notNull(context, "MRSContext is null. maybe not inited!");
        return ServiceLoggerContext.context;
    }
    
    /**
     * 
     * 获取日志对象
     *
     * @param logObjectType
     *            
     * @return ServiceLogger<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月24日]
     * @author rain
     */
    public static <T> LoggerService<T> getLogger(Class<T> logObjectType) {
        return ServiceLoggerContext.getContext().getServiceLogger(logObjectType);
    }
    
    /**
     * 
     * 记录日志<br>
     *
     * @param log 日志
     * @param logObjectType 日志类型
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年12月17日]
     * @author rain
     */
    public static <T> void log(T log, Class<T> logObjectType) {
        ServiceLoggerContext.getLogger(logObjectType).log(log);
    }
    
    /**
     * 
     * 记录日志<br>
     * 如果没有初始化 ServiceContext 则不会记录日志
     *
     * @param log 日志
     * @param logObjectType 日志类型
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年12月17日]
     * @author rain
     */
    public static <T> void logWithNotInitContext(T log, Class<T> logObjectType) {
        if (ServiceLoggerContext.context != null) {
            ServiceLoggerContext.getLogger(logObjectType).log(log);
        }
    }
}
