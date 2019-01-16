///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2018年6月3日
// * <修改描述:>
// */
//package com.tx.core.support.log4j2;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.logging.log4j.web.Log4jServletFilter;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.mybatis.support.EntityDaoRegistry;
//import com.tx.core.mybatis.support.MyBatisDaoSupport;
//import com.tx.core.starter.util.CoreUtilAutoConfiguration;
//
///**
// * mybatisSupport自动配置类<br/>
// * 定义一个类继承LogEventPatternConverter
// *      1.定义的这个类必须提供一个newInstance方法，参数是final String[] options，返回值为定义的类（对于是否是单例没有明确的要求）
// *      2.提供一个私有的构造函数，调用父类的构造函数，函数需要提供两个参数 第一个参数是转换器的名称，第二个是css样式，
// *      3.还有主要的工作format，这里有两个参数，LogEvent是系统已经存在的一些可选数据，StringBuilder 表示的是最终的输出字符流。一般都是将自定义的append进去
// *      4.注解：Plugin 表示的是这是一个插件，name是名称，category为PatternConverter.CATEGORY（目前插件只有这个选择）ConverterKeys表示的就是自定义的参数,可以多个
// * 
// * @author  Administrator
// * @version  [版本号, 2018年6月3日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Configuration
////@EnableConfigurationProperties(Log4jServletFilter.class)
////@ConditionalOnClass({ MybatisAutoConfiguration.class, SqlSessionFactory.class,SqlSessionFactoryBean.class })
////@ConditionalOnBean(value = { SqlSessionFactory.class, SqlSessionTemplate.class,MybatisAutoConfiguration.class })
////@AutoConfigureAfter({ CoreUtilAutoConfiguration.class,MybatisAutoConfiguration.class })
//public class Log4j2ExtentionAutoConfiguration
//        implements InitializingBean, ApplicationContextAware {
//    
//    private ApplicationContext applicationContext;
//    
//    /** 属性文件 */
//    private MybatisAutoConfigurationProperties properties;
//    
//    /** sqlSessionTemplate句柄 */
//    private SqlSessionTemplate sqlSessionTemplate;
//    
//    /** mybatis句柄 */
//    private MyBatisDaoSupport myBatisDaoSupport;
//    
//    /** <默认构造函数> */
//    public Log4j2ExtentionAutoConfiguration(
//            MybatisAutoConfigurationProperties properties) {
//        super();
//        this.properties = properties;
//    }
//    
//    /**
//     * @param applicationContext
//     * @throws BeansException
//     */
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext)
//            throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//    
//    /**
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        if (StringUtils.isEmpty(properties.getSqlSessionTemplateRef())
//                && this.applicationContext
//                        .getBeansOfType(SqlSessionTemplate.class).size() == 1) {
//            this.sqlSessionTemplate = this.applicationContext
//                    .getBean(SqlSessionTemplate.class);
//        } else {
//            AssertUtils.isTrue(
//                    this.applicationContext.containsBean(
//                            properties.getSqlSessionTemplateRef()),
//                    "sqlSessionTemplate:{} is not exist.",
//                    properties.getSqlSessionTemplateRef());
//            this.sqlSessionTemplate = this.applicationContext.getBean(
//                    properties.getSqlSessionTemplateRef(),
//                    SqlSessionTemplate.class);
//        }
//        
//        this.myBatisDaoSupport = new MyBatisDaoSupport(sqlSessionTemplate);
//    }
//}
