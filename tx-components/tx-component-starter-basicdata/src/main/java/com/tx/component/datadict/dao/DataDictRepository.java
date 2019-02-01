/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月1日
 * <修改描述:>
 */
package com.tx.component.datadict.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.datadict.model.DataDict;
import com.tx.core.paged.model.PagedList;

/**
 * 数据字典持久层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DataDictRepository {
    
    /**
     * 获取数据字典业务层实现的TransactionManager实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return PlatformTransactionManager [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PlatformTransactionManager geTransactionManager();
    
    public void batchInsert();
    
    public void batchDelete();
    
    public void batchUpdate();
    
    public void insert();
    
    public int delete();
    
    public DataDict find(DataDict datadict);
    
    public List<DataDict> query(Map<String, Object> params);
    
    public int count(Map<String, Object> params);
    
    public PagedList<DataDict> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize);
}
