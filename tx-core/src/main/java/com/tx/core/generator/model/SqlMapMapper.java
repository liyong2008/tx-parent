/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.generator.model;

import org.hibernate.dialect.Dialect;

import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.reflection.JpaMetaClass;

/**
 * 对应sqlMap顶层相关配置
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlMapMapper {
    
    /** 命名空间 */
    private String namespace;
    
    /** <默认构造函数> */
    public SqlMapMapper() {
        super();
    }
    
    /** <默认构造函数> */
    public SqlMapMapper(JpaMetaClass<?> jpaMetaClass, SqlSource<?> sqlSource,
            Dialect dialect) {
        super();
        this.namespace = org.apache.commons.lang.StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName());
    }
    
    /**
     * @return 返回 namespace
     */
    public String getNamespace() {
        return namespace;
    }
    
    /**
     * @param 对namespace进行赋值
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
