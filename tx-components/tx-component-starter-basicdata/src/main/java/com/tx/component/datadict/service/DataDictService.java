/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年2月1日
 * <修改描述:>
 */
package com.tx.component.datadict.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.datadict.dao.DataDictRepository;
import com.tx.component.datadict.model.DataDict;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 数据字典业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年2月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataDictService implements InitializingBean, ResourceLoaderAware {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(DataDictService.class);
    
    /** 资源加载器 */
    private ResourceLoader resourceLoader;
    
    
    private DataDictRepository dataDictReposity;
    
    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * 从数据库中加载数据字典数据<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private List<DataDict> loadListFromStore() {
        
        List<DataDict> ddList = this.dataDictDao.queryList(null);
        for (DataDict ddTemp : ddList) {
            if (!eeMultiMap.containsKey(ddTemp.getId())) {
                continue;
            }
            ddTemp.setEntryList(eeMultiMap.get(ddTemp.getId()));
        }
        return ddList;
    }
    
    /**
     * 向数据库中插入数据<br/>
     * <功能详细描述>
     * @param dataDict [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insert(DataDict dataDict);
    
    /**
     * @param entityId
     * @return
     */
    @Override
    protected boolean deleteEntityById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        DataDict condition = new DataDict();
        condition.setId(id);
        int resInt = this.dataDictDao.delete(condition);
        
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据Id查询DataDict实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return DataDict [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public DataDict findEntityById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        DataDict condition = new DataDict();
        condition.setId(id);
        
        DataDict res = this.dataDictDao.find(condition);
        return res;
    }
    
    /**
     * 根据Id查询DataDict实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return DataDict [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public DataDict findByCode(String basicDataTypeCode, String code) {
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        DataDict entity = findEntityByCode(basicDataTypeCode, code);
        
        //加载Entity的分项列表
        setupEntryList(entity);
        
        return entity;
    }
    
    /**
     * 根据Id查询DataDict实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return DataDict [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public DataDict findEntityByCode(String basicDataTypeCode, String code) {
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        DataDict condition = new DataDict();
        condition.setBasicDataTypeCode(basicDataTypeCode);
        condition.setCode(code);
        
        DataDict res = this.dataDictDao.find(condition);
        return res;
    }
    
    /**
     * 查询DataDict实体列表
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<DataDict> queryList(String basicDataTypeCode, Boolean valid,
            Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<DataDict> resList = this.dataDictDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询DataDict实体列表
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<DataDict> queryList(String basicDataTypeCode, String parentId,
            Boolean valid, Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("parentId", parentId);
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<DataDict> resList = this.dataDictDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 分页查询DataDict实体列表
     * <功能详细描述>
     * @param valid
      * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<DataDict> queryPagedList(String basicDataTypeCode,
            Boolean valid, Map<String, Object> params, int pageIndex,
            int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<DataDict> resPagedList = this.dataDictDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 分页查询DataDict实体列表
     * <功能详细描述>
     * @param valid
      * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<DataDict> queryPagedList(String basicDataTypeCode,
            String parentId, Boolean valid, Map<String, Object> params,
            int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("parentId", parentId);
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<DataDict> resPagedList = this.dataDictDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 判断是否已经存在<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public boolean isExist(String basicDataTypeCode,
            Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is empty");
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("basicDataTypeCode", basicDataTypeCode);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.dataDictDao.count(params);
        
        return res > 0;
    }
    
    /**
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public boolean updateEntityById(DataDict dataDict) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(dataDict, "dataDict is null.");
        AssertUtils.notEmpty(dataDict.getId(), "dataDict.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", dataDict.getId());
        
        //需要更新的字段
        //updateRowMap.put("valid", false);
        updateRowMap.put("name", dataDict.getName());
        updateRowMap.put("remark", dataDict.getRemark());
        updateRowMap.put("modifyAble", dataDict.isModifyAble());
        updateRowMap.put("lastUpdateDate", new Date());
        
        int updateRowCount = this.dataDictDao.update(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
    /**
     * 根据id禁用DataDict<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public boolean disableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", false);
        params.put("lastUpdateDate", new Date());
        
        this.dataDictDao.update(params);
        
        return true;
    }
    
    /**
      * 根据id启用DataDict<br/>
      * <功能详细描述>
      * @param postId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean enableById(String id);
}
