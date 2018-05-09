/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月3日
 * <修改描述:>
 */
package com.tx.component.task.starter;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.task.script.TaskContextTableInitializer;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 任务容器自动配置类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
@EnableConfigurationProperties(value = TaskContextProperties.class)
@ConditionalOnBean({ DataSource.class, PlatformTransactionManager.class })
@AutoConfigureAfter({ DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class })
@ConditionalOnProperty(prefix = "task", value = "enable", havingValue = "true")
public class TaskContextAutoConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    /** 任务容器属性 */
    private TaskContextProperties properties;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** 事务管理器 */
    private PlatformTransactionManager transactionManager;
    
    /** <默认构造函数> */
    public TaskContextAutoConfiguration(TaskContextProperties properties) {
        super();
        this.properties = properties;
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
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //设置dataSource
        if (StringUtils.isNotBlank(this.properties.getDataSource())
                && this.applicationContext
                        .containsBean(this.properties.getDataSource())) {
            this.dataSource = this.applicationContext
                    .getBean(this.properties.getDataSource(), DataSource.class);
        } else if (this.applicationContext.getBeansOfType(DataSource.class)
                .size() == 1) {
            this.dataSource = this.applicationContext.getBean(DataSource.class);
        }
        AssertUtils.notEmpty(this.dataSource,
                "dataSource is null.存在多个数据源，需要通过basicdata.dataSource指定使用的数据源,或为数据源设置为Primary.");
        
        //设置transactionManager
        if (StringUtils.isNotBlank(this.properties.getTransactionManager())
                && this.applicationContext.containsBean(
                        this.properties.getTransactionManager())) {
            this.transactionManager = this.applicationContext.getBean(
                    this.properties.getTransactionManager(),
                    PlatformTransactionManager.class);
        } else if (this.applicationContext
                .getBeansOfType(PlatformTransactionManager.class).size() == 1) {
            this.transactionManager = this.applicationContext
                    .getBean(PlatformTransactionManager.class);
        }
        AssertUtils.notEmpty(this.transactionManager,
                "transactionManager is null.存在多个事务管理器，需要通过basicdata.transactionManager指定使用的数据源,或为数据源设置为Primary.");
    }
    
    /**
     * 该类会优先加载:基础数据容器表初始化器<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2018年5月5日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ConditionalOnBean({ TableDDLExecutor.class })
    @ConditionalOnSingleCandidate(TableDDLExecutor.class)
    @ConditionalOnProperty(prefix = "task", value = "table-auto-initialize", havingValue = "true")
    @ConditionalOnMissingBean(TaskContextTableInitializer.class)
    public static class TaskContextTableInitializerConfiguration {
        
        /** 表ddl自动执行器 */
        private TableDDLExecutor tableDDLExecutor;
        
        public TaskContextTableInitializerConfiguration(
                TableDDLExecutor tableDDLExecutor) {
            this.tableDDLExecutor = tableDDLExecutor;
        }
        
        @Bean("taskContext.tableInitializer")
        public TaskContextTableInitializer tableInitializer() {
            TaskContextTableInitializer initializer = new TaskContextTableInitializer(
                    this.tableDDLExecutor);
            return initializer;
        }
    }
    
}
