/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.command.starter;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.component.command.context.CommandContext;
import com.tx.component.command.context.CommandContextFactory;
import com.tx.component.strategy.context.StrategyContext;
import com.tx.component.strategy.context.StrategyContextFactory;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 命令容器配置器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(value = CommandContextProperties.class)
@ConditionalOnProperty(prefix = "command", name = "datasource")
//@ConditionalOnBean(DataSource.class)
//@ConditionalOnClass(CommandContext.class)
public class CommandContextAutoConfiguration
        implements ApplicationContextAware {
    
    @Autowired
    private CommandContextProperties commandContextProperties;
    
    private ApplicationContext applicationContext;
    
    /**
     * @param arg0
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * 当命令容器不存在时<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CommandContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean("commandContext")
    @ConditionalOnMissingBean(CommandContext.class)
    public CommandContextFactory commandContext() {
        AssertUtils.notNull(commandContextProperties.getDatasource(),
                "命令容器需要配置其数据源: command.datasource");
        
//        Map<String, DataSource> dsMap = this.applicationContext
//                .getBeansOfType(DataSource.class);
//        for (String beanNameTemp : dsMap.keySet()) {
//            System.out.println(beanNameTemp);
//        }
//        DataSource datasource = this.applicationContext.getBean(
//                DataSource.class, commandContextProperties.getDatasource());
//        AssertUtils.notNull(datasource, "命令容器需要配置其数据源: dataSource is null");
        
        CommandContextFactory factory = new CommandContextFactory();
        factory.setDataSource(null);
        
        return factory;
    }
    
    /**
     * 自动加载策略容器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return StrategyContextFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean("strategyContext")
    @ConditionalOnMissingBean(StrategyContext.class)
    public StrategyContextFactory strategyContext() {
        StrategyContextFactory factory = new StrategyContextFactory();
        
        return factory;
    }
}
