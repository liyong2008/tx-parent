/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月15日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.support.entrysupport.model.SerializableEntryAbleFieldEntity;

/**
 * 实体分项反射类描述<br/>
 *     暂不用去支持Column等注解<br/>
 *     entry中附加属性如果需要作为查询条件不建议使用该类进行应用<br/>
 *     一般适用于其他字段仅仅是附加属性并且为简单类型，复杂类型不应适用于该类型实现<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MetaEntityEntry {
    
    //实体分项基本字段
    private static final Set<String> entityEntryCommonFieldNames = new HashSet<>(
            Arrays.asList("id".toUpperCase(),
                    "entityId".toUpperCase(),
                    "entryKey".toUpperCase(),
                    "entryValue".toUpperCase()));
    
    //实例表后缀
    private static final String hisTableNameSuffix = "_his";
    
    //插入语句模板
    //"INSERT INTO {} (id ,entityId ,entryKey ,entryValue {}) VALUES(:id ,:entityId ,:entryKey ,:entryValue {})";
    private static final String INSERT_SQL_TEMPLATE = (new StringBuilder(
            "INSERT INTO ")).append("{} ")
            .append("(id ,entityId ,entryKey ,entryValue {}) ")
            .append("VALUES(:id ,:entityId ,:entryKey ,:entryValue {})")
            .toString();
    
    //DELETE FROM {} WHERE entityId = :entityId
    private static final String DELETE_BY_ENTITYID_SQL_TEMPLATE = (new StringBuilder(
            "DELETE FROM {} WHERE entityId = :entityId")).toString();
    
    //DELETE FROM {} WHERE id = :id
    private static final String DELETE_BY_ENTRYID_SQL_TEMPLATE = (new StringBuilder(
            "DELETE FROM {} WHERE id = :id")).toString();
    
    //DELETE FROM {} WHERE entryKey = :entryKey AND entityId = :entityId
    private static final String DELETE_BY_ENTRYKEY_SQL_TEMPLATE = (new StringBuilder(
            "DELETE FROM {} WHERE entryKey = :entryKey AND entityId = :entityId")).toString();
    
    //UPDATE {} SET entryValue = :entryValue {} WHERE ID = :id
    private static final String UPDATE_BY_ENTRYID_SQL_TEMPLATE = (new StringBuilder(
            "UPDATE {} SET entryValue = :entryValue {} WHERE id = :id")).toString();
    
    //UPDATE {} SET entryValue = :entryValue {} WHERE entryKey = :entryKey AND entityId = :entityId
    private static final String UPDATE_BY_ENTRYKEY_SQL_TEMPLATE = (new StringBuilder(
            "UPDATE {} SET entryValue = :entryValue {} WHERE entryKey = :entryKey AND entityId = :entityId")).toString();
    
    //SELECT * FROM {} WHERE id = :id
    private static final String FIND_BY_ENTRYID_SQL_TEMPLATE = (new StringBuilder(
            "SELECT * FROM {} WHERE id = :id")).toString();
    
    //SELECT * FROM {} WHERE entryKey = :entryKey AND entityId = :entityId
    private static final String FIND_BY_ENTRYKEY_SQL_TEMPLATE = (new StringBuilder(
            "SELECT * FROM {} WHERE entryKey = :entryKey AND entityId = :entityId")).toString();
    
    //SELECT * FROM {} WHERE entityId = :entityId
    private static final String QUERY_BY_ENTITYID_SQL_TEMPLATE = (new StringBuilder(
            "SELECT * FROM {} WHERE entityId = :entityId")).toString();
    
    /** 实体分项解析类映射 */
    private static Map<String, MetaEntityEntry> metaEntityEntryMap = new WeakHashMap<>();
    
    /** 对应的类型 */
    private final Class<?> type;
    
    /** RowMapper */
    private final RowMapper<?> rowMap;
    
    /** jpaMetaClass */
    private final JpaMetaClass<?> jpaMetaClass;
    
    /** 表名 */
    private final String tableName;
    
    /** 历史表：表名 */
    private final String hisTableName;
    
    /** 其他字段名称 */
    private final List<String> otherFieldName;
    
    private final String otherColumnsForInsert;
    
    private final String otherFieldsForInsert;
    
    private final String otherColumn2FieldForUpdate;
    
    private final String sqlOfInsert;
    
    private final String sqlOfInsertToHis;
    
    private final String sqlOfDeleteByEntityId;
    
    private final String sqlOfDeleteById;
    
    private final String sqlOfDeleteByEntryKey;
    
    private final String sqlOfUpdateById;
    
    private final String sqlOfUpdateEntryKey;
    
    private final String sqlOfFindById;
    
    private final String sqlOfFindByEntryKey;
    
    private final String sqlOfQueryListByEntityId;
    
    /**
      * 获取对应的EntityEntry的解析类<br/>
      * <功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return MetaEntityEntry [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static MetaEntityEntry forClass(Class<?> type) {
        MetaEntityEntry meta = forClass(type, null);
        return meta;
    }
    
    /**
      * 获取对应的EntityEntry的解析类<br/>
      * <功能详细描述>
      * @param type
      * @param tableName
      * @return [参数说明]
      * 
      * @return MetaEntityEntry [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static MetaEntityEntry forClass(Class<?> type, String tableName) {
        AssertUtils.notNull(type, "type is null.");
        String typeKey = type.getName() + "_"
                + (tableName == null ? "" : tableName.toUpperCase());
        MetaEntityEntry meta = null;
        if (metaEntityEntryMap.containsKey(typeKey)) {
            meta = metaEntityEntryMap.get(typeKey);
        } else {
            meta = new MetaEntityEntry(type, tableName);
            metaEntityEntryMap.put(typeKey, meta);
        }
        return meta;
    }
    
    /** <默认构造函数> */
    private MetaEntityEntry(Class<?> type, String tableName) {
        super();
        this.type = type;
        this.jpaMetaClass = JpaMetaClass.forClass(type);
        this.rowMap = new BeanPropertyRowMapper<>(type);
        if (StringUtils.isEmpty(tableName)) {
            this.tableName = this.jpaMetaClass.getTableName();
        } else {
            this.tableName = tableName;
        }
        this.hisTableName = this.tableName + hisTableNameSuffix;
        this.otherFieldName = new ArrayList<>();
        Set<String> getterNames = this.jpaMetaClass.getGetterNames();
        for (String getterNameTemp : getterNames) {
            if (entityEntryCommonFieldNames.contains(getterNameTemp.toUpperCase())) {
                continue;
            }
            this.otherFieldName.add(getterNameTemp);
        }
        
        StringBuilder fieldsSB = new StringBuilder(
                TxConstants.INITIAL_STR_LENGTH);
        StringBuilder columnSB = new StringBuilder(
                TxConstants.INITIAL_STR_LENGTH);
        StringBuilder column2FieldSB = new StringBuilder(
                TxConstants.INITIAL_STR_LENGTH);
        for (String otherFieldNameTemp : this.otherFieldName) {
            columnSB.append(",").append(otherFieldNameTemp).append(" ");
            fieldsSB.append(",:").append(otherFieldNameTemp).append(" ");
            column2FieldSB.append(", ")
                    .append(otherFieldNameTemp)
                    .append(" = :")
                    .append(otherFieldNameTemp);
        }
        this.otherFieldsForInsert = fieldsSB.toString();
        this.otherColumnsForInsert = columnSB.toString();
        this.otherColumn2FieldForUpdate = column2FieldSB.toString();
        
        //INSERT INTO {} (id ,entityId ,entryKey ,entryValue {}) VALUES(:id ,:entityId ,:entryKey ,:entryValue {})
        this.sqlOfInsert = MessageFormatter.arrayFormat(INSERT_SQL_TEMPLATE,
                new Object[] { this.tableName, this.otherColumnsForInsert,
                        this.otherFieldsForInsert }).getMessage();
        //INSERT INTO {} (id ,entityId ,entryKey ,entryValue {}) VALUES(:id ,:entityId ,:entryKey ,:entryValue {})
        this.sqlOfInsertToHis = MessageFormatter.arrayFormat(INSERT_SQL_TEMPLATE,
                new Object[] { this.hisTableName, this.otherColumnsForInsert,
                        this.otherFieldsForInsert })
                .getMessage();
        //DELETE FROM {} WHERE entityId = :entityId
        this.sqlOfDeleteByEntityId = MessageFormatter.arrayFormat(DELETE_BY_ENTITYID_SQL_TEMPLATE,
                new Object[] { this.tableName })
                .getMessage();
        //DELETE FROM {} WHERE id = :id
        this.sqlOfDeleteById = MessageFormatter.arrayFormat(DELETE_BY_ENTRYID_SQL_TEMPLATE,
                new Object[] { this.tableName })
                .getMessage();
        //DELETE FROM {} WHERE entryKey = :entryKey AND entityId = :entityId
        this.sqlOfDeleteByEntryKey = MessageFormatter.arrayFormat(DELETE_BY_ENTRYKEY_SQL_TEMPLATE,
                new Object[] { this.tableName })
                .getMessage();
        //UPDATE {} SET entryValue = :entryValue {} WHERE ID = :id
        this.sqlOfUpdateById = MessageFormatter.arrayFormat(UPDATE_BY_ENTRYID_SQL_TEMPLATE,
                new Object[] { this.tableName, this.otherColumn2FieldForUpdate })
                .getMessage();
        //UPDATE {} SET entryValue = :entryValue {} WHERE entryKey = :entryKey AND entityId = :entityId
        this.sqlOfUpdateEntryKey = MessageFormatter.arrayFormat(UPDATE_BY_ENTRYKEY_SQL_TEMPLATE,
                new Object[] { this.tableName, this.otherColumn2FieldForUpdate })
                .getMessage();
        //SELECT * FROM {} WHERE id = :id
        this.sqlOfFindById = MessageFormatter.arrayFormat(FIND_BY_ENTRYID_SQL_TEMPLATE,
                new Object[] { this.tableName })
                .getMessage();
        //SELECT * FROM {} WHERE entryKey = :entryKey AND entityId = :entityId
        this.sqlOfFindByEntryKey = MessageFormatter.arrayFormat(FIND_BY_ENTRYKEY_SQL_TEMPLATE,
                new Object[] { this.tableName })
                .getMessage();
        //SELECT * FROM {} WHERE entityId = :entityId
        this.sqlOfQueryListByEntityId = MessageFormatter.arrayFormat(QUERY_BY_ENTITYID_SQL_TEMPLATE,
                new Object[] { this.tableName })
                .getMessage();
    }
    
    /**
     * @return 返回 type
     */
    public Class<?> getType() {
        return type;
    }
    
    /**
     * @return 返回 jpaMetaClass
     */
    public JpaMetaClass<?> getJpaMetaClass() {
        return jpaMetaClass;
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @return 返回 hisTableName
     */
    public String getHisTableName() {
        return hisTableName;
    }
    
    /**
     * @return 返回 sqlOfInsert
     */
    public String getSqlOfInsert() {
        return sqlOfInsert;
    }
    
    /**
     * @return 返回 sqlOfDeleteById
     */
    public String getSqlOfDeleteById() {
        return sqlOfDeleteById;
    }
    
    /**
     * @return 返回 sqlOfDeleteByEntryKey
     */
    public String getSqlOfDeleteByEntryKey() {
        return sqlOfDeleteByEntryKey;
    }
    
    /**
     * @return 返回 sqlOfUpdateById
     */
    public String getSqlOfUpdateById() {
        return sqlOfUpdateById;
    }
    
    /**
     * @return 返回 sqlOfUpdateEntryKey
     */
    public String getSqlOfUpdateEntryKey() {
        return sqlOfUpdateEntryKey;
    }
    
    /**
     * @return 返回 sqlOfFindById
     */
    public String getSqlOfFindById() {
        return sqlOfFindById;
    }
    
    /**
     * @return 返回 sqlOfFindByEntryKey
     */
    public String getSqlOfFindByEntryKey() {
        return sqlOfFindByEntryKey;
    }
    
    /**
     * @return 返回 sqlOfQueryListByEntityId
     */
    public String getSqlOfQueryListByEntityId() {
        return sqlOfQueryListByEntityId;
    }
    
    /**
     * @return 返回 sqlOfDeleteByEntityId
     */
    public String getSqlOfDeleteByEntityId() {
        return sqlOfDeleteByEntityId;
    }
    
    /**
     * @return 返回 sqlOfInsertToHis
     */
    public String getSqlOfInsertToHis() {
        return sqlOfInsertToHis;
    }
    
    /**
     * @return 返回 rowMap
     */
    public RowMapper<?> getRowMap() {
        return rowMap;
    }
    
    public static void main(String[] args) {
        Class<?> type = SerializableEntryAbleFieldEntity.class;
        MetaEntityEntry mee = MetaEntityEntry.forClass(type, "t_test_entry");
        
        System.out.println(mee.getSqlOfInsert());
        System.out.println(mee.getSqlOfInsertToHis());
        
        System.out.println(mee.getSqlOfDeleteByEntityId());
        System.out.println(mee.getSqlOfDeleteById());
        System.out.println(mee.getSqlOfDeleteByEntryKey());
        
        System.out.println(mee.getSqlOfUpdateById());
        System.out.println(mee.getSqlOfUpdateEntryKey());
        
        System.out.println(mee.getSqlOfFindById());
        System.out.println(mee.getSqlOfFindByEntryKey());
        
        System.out.println(mee.getSqlOfQueryListByEntityId());
    }
}
