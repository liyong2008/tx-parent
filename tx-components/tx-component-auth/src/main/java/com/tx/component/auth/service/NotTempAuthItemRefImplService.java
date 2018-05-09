/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tx.component.auth.context.AuthContext;
import com.tx.component.auth.context.AuthSessionContext;
import com.tx.component.auth.dao.AuthItemRefImplDao;
import com.tx.component.auth.dao.impl.AuthItemRefImplDaoImpl;
import com.tx.component.auth.model.Auth;
import com.tx.component.auth.model.AuthItemRef;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * AuthItemRefImpl的业务层<br/>
 *     
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NotTempAuthItemRefImplService {
    
    /** 事务管理器 */
    private PlatformTransactionManager txManager;
    
    /** 权限引用项持久层 */
    @Resource(name = "authItemRefImplDao")
    private AuthItemRefImplDao authItemRefImplDao;
    
    /** <默认构造函数> */
    public NotTempAuthItemRefImplService() {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public NotTempAuthItemRefImplService(PlatformTransactionManager txManager) {
        super();
        AssertUtils.notNull(txManager, "txManager is null.");
        this.txManager = txManager;
    }
    
    /**
     * <默认构造函数>
     */
    public NotTempAuthItemRefImplService(PlatformTransactionManager txManager,
            JdbcTemplate jdbcTemplate) {
        super();
        AssertUtils.notNull(txManager, "txManager is null.");
        AssertUtils.notNull(jdbcTemplate, "jdbcTemplate is null.");
        this.txManager = txManager;
        
        this.authItemRefImplDao = new AuthItemRefImplDaoImpl(jdbcTemplate);
    }
    
    /**
     * 根据引用id以及权限引用类型查询权限引用集合<br/>
     * <功能详细描述>
     * 
     * @param operatorId
     * @param authRefType
     * @return [参数说明]
     * 
     * @return List<AuthItemRef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefListByRefTypeAndRefId(
            String authRefType, String refId, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refId", refId);
        params.put("authRefType", authRefType);
        params.put("systemId", systemId);
        params.put("temp", false);
        
        List<AuthItemRef> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
      * 根据权限类型，权限引用类型，以及引用id查询权限引用列表<br/>
      * <功能详细描述>
      * @param authType
      * @param authRefType
      * @param refId
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefListByAuthTypeAndRefTypeAndRefId(
            String authType, String authRefType, String refId, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(refId, "refId is empty.");
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authType", authType);
        params.put("refId", refId);
        params.put("authRefType", authRefType);
        params.put("systemId", systemId);
        params.put("temp", false);
        
        List<AuthItemRef> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
      * 根据引用类型以及权限项id获取，对应的引用类型中有哪些引用实体id引用了该权限<br/>
      * <功能详细描述>
      * @param authRefType
      * @param refId
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefListByRefTypeAndAuthItemId(
            String authRefType, String authItemId, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        AssertUtils.notEmpty(authItemId, "refId is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authRefType", authRefType);
        params.put("authItemId", authItemId);
        params.put("systemId", systemId);
        params.put("temp", false);
        
        List<AuthItemRef> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
      * 根据权限类型，权限引用类型，以及权限id差选权限引用集合
      * <功能详细描述>
      * @param authType
      * @param authRefType
      * @param authItemId
      * @return [参数说明]
      * 
      * @return List<AuthItemRefImpl> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<AuthItemRef> queryAuthItemRefListByAuthTypeAndRefTypeAndAuthItemId(
            String authType, String authRefType, String authItemId,
            String systemId, String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty.");
        AssertUtils.notEmpty(authItemId, "refId is empty.");
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authType", authType);
        params.put("authRefType", authRefType);
        params.put("authItemId", authItemId);
        params.put("systemId", systemId);
        params.put("temp", false);
        
        List<AuthItemRef> authItemRefImplList = this.authItemRefImplDao.queryAuthItemRefImplList(params,
                tableSuffix);
        
        return authItemRefImplList;
    }
    
    /**
     * 增加权限项目引用
     *     需要传入，引用类型，权限项ID，引用id集合
     * <功能详细描述>
     * @param authRefType
     * @param authId
     * @param refIdList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void addAuthItemOfAuthRefList(String authRefType, String authItemId,
            List<String> addRefIdList, String systemId, String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(authItemId, "authItemId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        if (addRefIdList == null) {
            addRefIdList = new ArrayList<String>();
        }
        
        List<String> srcAuthRefIds = new ArrayList<String>();
        List<AuthItemRef> authItemRefImplList = queryAuthItemRefListByRefTypeAndAuthItemId(authRefType,
                authItemId,
                systemId,
                tableSuffix);
        if (authItemRefImplList != null) {
            for (AuthItemRef refTemp : authItemRefImplList) {
                srcAuthRefIds.add(refTemp.getRefId());
            }
        }
        
        @SuppressWarnings("unchecked")
        List<String> needInsertRefIds = ListUtils.subtract(addRefIdList,
                srcAuthRefIds);
        
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("authItemRefImplServiceTxName");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = this.txManager.getTransaction(def);
        
        try {
            batchInsertAuthItemRefByRefIds(authRefType,
                    authItemId,
                    needInsertRefIds,
                    systemId,
                    tableSuffix);
        } catch (DataAccessException e) {
            this.txManager.rollback(status);
            throw e;
        }
        this.txManager.commit(status);
    }
    
    /**
     * 增加权限项目引用
     *     需要传入，引用类型，权限项ID，引用id集合
     * <功能详细描述>
     * @param authRefType
     * @param authId
     * @param refIdList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void deleteAuthItemOfAuthRefList(String authRefType,
            String authItemId, List<String> deleteRefIdList, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(authItemId, "authItemId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        if (deleteRefIdList == null) {
            deleteRefIdList = new ArrayList<String>();
        }
        
        List<String> srcAuthRefIds = new ArrayList<String>();
        List<AuthItemRef> authItemRefImplList = queryAuthItemRefListByRefTypeAndAuthItemId(authRefType,
                authItemId,
                systemId,
                tableSuffix);
        Map<String, AuthItemRef> dbAuthItemRefMap = new HashMap<String, AuthItemRef>();
        if (authItemRefImplList != null) {
            for (AuthItemRef refTemp : authItemRefImplList) {
                srcAuthRefIds.add(refTemp.getRefId());
                dbAuthItemRefMap.put(refTemp.getRefId(), refTemp);
            }
        }
        
        @SuppressWarnings("unchecked")
        List<String> needDeleteRefIds = ListUtils.intersection(srcAuthRefIds,
                deleteRefIdList);
        //生成需要插入权限引用历史表的数据
        List<AuthItemRef> needInsertToHis = new ArrayList<AuthItemRef>();
        for (String needDeleteRefIdTemp : needDeleteRefIds) {
            if (dbAuthItemRefMap.containsKey(needDeleteRefIdTemp)) {
                needInsertToHis.add(dbAuthItemRefMap.get(needDeleteRefIdTemp));
            }
        }
        
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("authItemRefImplServiceTxName");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = this.txManager.getTransaction(def);
        
        try {
            batchInsertAuthItemRefToHis(needInsertToHis, tableSuffix);
            batchDeleteAuthItemRefByRefIds(authRefType,
                    authItemId,
                    needDeleteRefIds,
                    systemId,
                    tableSuffix);
        } catch (DataAccessException e) {
            this.txManager.rollback(status);
            throw e;
        }
        this.txManager.commit(status);
    }
    
    /**
     * 增加权限项目引用
     *     需要传入，引用类型，权限项ID，引用id集合
     * <功能详细描述>
     * @param authRefType
     * @param authId
     * @param refIdList [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void saveAuthItemOfAuthRefList(String authRefType,
            String authItemId, List<String> addRefIdList,
            List<String> deleteRefIdList, String systemId, String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(authItemId, "authItemId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        if (addRefIdList == null) {
            addRefIdList = new ArrayList<String>();
        }
        if (deleteRefIdList == null) {
            deleteRefIdList = new ArrayList<String>();
        }
        
        List<String> srcAuthRefIds = new ArrayList<String>();
        List<AuthItemRef> authItemRefImplList = queryAuthItemRefListByRefTypeAndAuthItemId(authRefType,
                authItemId,
                systemId,
                tableSuffix);
        Map<String, AuthItemRef> dbAuthItemRefMap = new HashMap<String, AuthItemRef>();
        if (authItemRefImplList != null) {
            for (AuthItemRef refTemp : authItemRefImplList) {
                srcAuthRefIds.add(refTemp.getRefId());
                dbAuthItemRefMap.put(refTemp.getRefId(), refTemp);
            }
        }
        
        @SuppressWarnings("unchecked")
        List<String> needInsertRefIds = ListUtils.subtract(addRefIdList,
                srcAuthRefIds);
        @SuppressWarnings("unchecked")
        List<String> needDeleteRefIds = ListUtils.intersection(srcAuthRefIds,
                deleteRefIdList);
        //生成需要插入权限引用历史表的数据
        List<AuthItemRef> needInsertToHis = new ArrayList<AuthItemRef>();
        for (String needDeleteRefIdTemp : needDeleteRefIds) {
            if (dbAuthItemRefMap.containsKey(needDeleteRefIdTemp)) {
                needInsertToHis.add(dbAuthItemRefMap.get(needDeleteRefIdTemp));
            }
        }
        
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("authItemRefImplServiceTxName");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = this.txManager.getTransaction(def);
        
        try {
            batchInsertAuthItemRefToHis(needInsertToHis, tableSuffix);
            batchDeleteAuthItemRefByRefIds(authRefType,
                    authItemId,
                    needDeleteRefIds,
                    systemId,
                    tableSuffix);
            batchInsertAuthItemRefByRefIds(authRefType,
                    authItemId,
                    needInsertRefIds,
                    systemId,
                    tableSuffix);
        } catch (DataAccessException e) {
            this.txManager.rollback(status);
            throw e;
        }
        this.txManager.commit(status);
    }
    
    /**
      * 保存权限项目引用
      *     需要传入，引用类型，权限项ID，引用id集合
      * <功能详细描述>
      * @param authRefType
      * @param authId
      * @param refIdList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void saveAuthItemOfAuthRefList(String authRefType,
            String authItemId, List<String> refIdList, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(authItemId, "authItemId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        if (refIdList == null) {
            refIdList = new ArrayList<String>();
        }
        
        List<String> srcAuthRefIds = new ArrayList<String>();
        List<AuthItemRef> authItemRefImplList = queryAuthItemRefListByRefTypeAndAuthItemId(authRefType,
                authItemId,
                systemId,
                tableSuffix);
        Map<String, AuthItemRef> dbAuthItemRefMap = new HashMap<String, AuthItemRef>();
        if (authItemRefImplList != null) {
            for (AuthItemRef refTemp : authItemRefImplList) {
                srcAuthRefIds.add(refTemp.getRefId());
                dbAuthItemRefMap.put(refTemp.getRefId(), refTemp);
            }
        }
        
        @SuppressWarnings("unchecked")
        List<String> needDeleteRefIds = ListUtils.subtract(srcAuthRefIds,
                refIdList);
        @SuppressWarnings("unchecked")
        List<String> needInsertRefIds = ListUtils.subtract(refIdList,
                srcAuthRefIds);
        //生成需要插入权限引用历史表的数据
        List<AuthItemRef> needInsertToHis = new ArrayList<AuthItemRef>();
        for (String needDeleteRefIdTemp : needDeleteRefIds) {
            if (dbAuthItemRefMap.containsKey(needDeleteRefIdTemp)) {
                needInsertToHis.add(dbAuthItemRefMap.get(needDeleteRefIdTemp));
            }
        }
        
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("authItemRefImplServiceTxName");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = this.txManager.getTransaction(def);
        
        try {
            batchInsertAuthItemRefToHis(needInsertToHis, tableSuffix);
            batchDeleteAuthItemRefByRefIds(authRefType,
                    authItemId,
                    needDeleteRefIds,
                    systemId,
                    tableSuffix);
            batchInsertAuthItemRefByRefIds(authRefType,
                    authItemId,
                    needInsertRefIds,
                    systemId,
                    tableSuffix);
        } catch (DataAccessException e) {
            this.txManager.rollback(status);
            throw e;
        }
        this.txManager.commit(status);
    }
    
    /**
      * 根据存入的权限项目id集合，以及权限引用类型，引用id<br/>
      *     更新对应应用的权限集<br/>
      * <功能详细描述>
      * @param authRefType
      * @param refId
      * @param authItemIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void saveAuthRefOfAuthItemList(String authRefType, String refId,
            List<String> authItemIds, String systemId, String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(refId, "refId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        if (authItemIds == null) {
            authItemIds = new ArrayList<String>();
        }
        
        //存储前,获取原有的权限引用
        List<String> srcAuthItemIds = new ArrayList<String>();
        List<AuthItemRef> authItemRefImplList = queryAuthItemRefListByRefTypeAndRefId(authRefType,
                refId,
                systemId,
                tableSuffix);
        Map<String, AuthItemRef> dbAuthItemRefMap = new HashMap<String, AuthItemRef>();
        if (authItemRefImplList != null) {
            for (AuthItemRef refTemp : authItemRefImplList) {
                srcAuthItemIds.add(refTemp.getAuthItem().getId());
                dbAuthItemRefMap.put(refTemp.getAuthItem().getId(), refTemp);
            }
        }
        
        //求差集，得到需要删除，以及需要增加的权限id集合
        @SuppressWarnings("unchecked")
        List<String> needDeleteAuthItemIds = ListUtils.subtract(srcAuthItemIds,
                authItemIds);
        @SuppressWarnings("unchecked")
        List<String> needInsertAuthItemIds = ListUtils.subtract(authItemIds,
                srcAuthItemIds);
        //生成需要插入权限引用历史表的数据
        List<AuthItemRef> needInsertToHis = new ArrayList<AuthItemRef>();
        for (String needDeleteAuthItemIdTemp : needDeleteAuthItemIds) {
            if (dbAuthItemRefMap.containsKey(needDeleteAuthItemIdTemp)
                    && AuthContext.getContext()
                            .getAllAuthItemMapping()
                            .containsKey(needDeleteAuthItemIdTemp)) {
                needInsertToHis.add(dbAuthItemRefMap.get(needDeleteAuthItemIdTemp));
            }
        }
        
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("authItemRefImplServiceTxName");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = this.txManager.getTransaction(def);
        
        try {
            batchInsertAuthItemRefToHis(needInsertToHis, tableSuffix);
            batchDeleteAuthItemRefByAuthItemIds(authRefType,
                    refId,
                    needDeleteAuthItemIds,
                    systemId,
                    tableSuffix);
            batchInsertAuthItemRefByAuthItemIds(authRefType,
                    refId,
                    needInsertAuthItemIds,
                    systemId,
                    tableSuffix);
        } catch (DataAccessException e) {
            this.txManager.rollback(status);
            throw e;
        }
        this.txManager.commit(status);
    }
    
    /**
      * 根据存入的权限项目id集合，以及权限引用类型，引用id，权限类型<br/>
      *     更新对应应用的权限集<br/>
      * <功能详细描述>
      * @param authType
      * @param authRefType
      * @param refId
      * @param authItemIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void saveAuthRefOfAuthItemList(String authType, String authRefType,
            String refId, List<String> authItemIds, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(authRefType, "authRefType is empty");
        AssertUtils.notEmpty(refId, "refId is empty");
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        if (authItemIds == null) {
            authItemIds = new ArrayList<String>();
        }
        
        //存储前,获取原有的权限引用
        List<String> srcAuthItemIds = new ArrayList<String>();
        List<AuthItemRef> authItemRefImplList = queryAuthItemRefListByAuthTypeAndRefTypeAndRefId(authType,
                authRefType,
                refId,
                systemId,
                tableSuffix);
        Map<String, AuthItemRef> dbAuthItemRefMap = new HashMap<String, AuthItemRef>();
        if (authItemRefImplList != null) {
            for (AuthItemRef refTemp : authItemRefImplList) {
                srcAuthItemIds.add(refTemp.getAuthItem().getId());
                dbAuthItemRefMap.put(refTemp.getAuthItem().getId(), refTemp);
            }
        }
        
        //求差集，得到需要删除，以及需要增加的权限id集合
        @SuppressWarnings("unchecked")
        List<String> needDeleteAuthItemIds = ListUtils.subtract(srcAuthItemIds,
                authItemIds);
        @SuppressWarnings("unchecked")
        List<String> needInsertAuthItemIds = ListUtils.subtract(authItemIds,
                srcAuthItemIds);
        //生成需要插入权限引用历史表的数据
        List<AuthItemRef> needInsertToHis = new ArrayList<AuthItemRef>();
        for (String needDeleteAuthItemIdTemp : needDeleteAuthItemIds) {
            if (dbAuthItemRefMap.containsKey(needDeleteAuthItemIdTemp)
                    && AuthContext.getContext()
                            .getAllAuthItemMapping()
                            .containsKey(needDeleteAuthItemIdTemp)) {
                needInsertToHis.add(dbAuthItemRefMap.get(needDeleteAuthItemIdTemp));
            }
        }
        
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("authItemRefImplServiceTxName");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = this.txManager.getTransaction(def);
        
        try {
            batchInsertAuthItemRefToHis(needInsertToHis, tableSuffix);
            batchDeleteAuthItemRefByAuthItemIds(authRefType,
                    refId,
                    needDeleteAuthItemIds,
                    systemId,
                    tableSuffix);
            batchInsertAuthItemRefByAuthItemIds(authRefType,
                    refId,
                    needInsertAuthItemIds,
                    systemId,
                    tableSuffix);
        } catch (DataAccessException e) {
            this.txManager.rollback(status);
            throw e;
        }
        this.txManager.commit(status);
    }
    
    /**
     * 删除权限引用项<br/>
     * <功能详细描述>
     * 
     * @param operatorId 当前操作员
     * @param refId
     * @param newAuthIds
     * @param authRefType
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void batchDeleteAuthItemRefByAuthItemIds(String authRefType,
            String refId, List<String> needDeleteAuthItemIds, String systemId,
            String tableSuffix) {
        if (CollectionUtils.isEmpty(needDeleteAuthItemIds)) {
            return;
        }
        AssertUtils.notEmpty(systemId, "systemId is empty.");
        
        //如果存在需要删除的权限引用项
        List<AuthItemRef> authItemRefList = new ArrayList<AuthItemRef>();
        for (String authItemIdTemp : needDeleteAuthItemIds) {
            AuthItemRef authItemRef = new AuthItemRef();
            authItemRef.setTemp(false);
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            //跳过系统中不存在的权限
            if (!AuthContext.getContext()
                    .getAllAuthItemMapping()
                    .containsKey(authItemIdTemp)) {
                continue;
            }
            Auth authItemTemp = AuthContext.getContext()
                    .getAuthItemFromContextById(authItemIdTemp);
            AssertUtils.notNull(authItemTemp,
                    "authItem is null.authItemId:{}",
                    authItemIdTemp);
            authItemRef.setAuthItem(authItemTemp);
            
            //添加进批量处理列表
            authItemRefList.add(authItemRef);
        }
        
        this.authItemRefImplDao.batchDeleteAuthItemRefImpl(authItemRefList,
                tableSuffix);
    }
    
    /**
      * 批量删除权限项的多个权限项目引用 
      * <功能详细描述>
      * @param authRefType
      * @param authItemId
      * @param needDeleteRefIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void batchDeleteAuthItemRefByRefIds(String authRefType,
            String authItemId, List<String> needDeleteRefIds, String systemId,
            String tableSuffix) {
        if (CollectionUtils.isEmpty(needDeleteRefIds)) {
            return;
        }
        AssertUtils.notEmpty(systemId, "systemId is empty");
        //跳过系统中不存在的权限
        if (!AuthContext.getContext()
                .getAllAuthItemMapping()
                .containsKey(authItemId)) {
            return;
        }
        
        Auth authItem = AuthContext.getContext()
                .getAuthItemFromContextById(authItemId);
        AssertUtils.notNull(authItem,
                "authItem is null.authItemId:{}",
                authItemId);
        
        //如果存在需要删除的权限引用项
        List<AuthItemRef> authItemRefList = new ArrayList<AuthItemRef>();
        for (String refIdTemp : needDeleteRefIds) {
            AuthItemRef authItemRef = new AuthItemRef();
            authItemRef.setTemp(false);
            
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setAuthItem(authItem);
            authItemRef.setRefId(refIdTemp);
            
            //添加进批量处理列表
            authItemRefList.add(authItemRef);
        }
        this.authItemRefImplDao.batchDeleteAuthItemRefImpl(authItemRefList,
                tableSuffix);
    }
    
    /**
     * 批量添加对权限引用的权限
     * 
     * @param userId
     * @param refId
     * @param newAuthIds
     * @param authRefType
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void batchInsertAuthItemRefByAuthItemIds(String authRefType,
            String refId, List<String> needInsertAuthItemIds, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(systemId, "systemId is empty");
        
        List<AuthItemRef> authItemRefList = new ArrayList<AuthItemRef>();
        
        //取得当前登录人员id
        String currentOperatorId = AuthSessionContext.getOperatorIdFromSession();
        
        for (String authItemIdTemp : needInsertAuthItemIds) {
            AuthItemRef authItemRef = new AuthItemRef();
            authItemRef.setTemp(false);
            authItemRef.setCreateDate(new Date());
            authItemRef.setCreateOperId(currentOperatorId);
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refId);
            //跳过系统中不存在的权限
            if (!AuthContext.getContext()
                    .getAllAuthItemMapping()
                    .containsKey(authItemIdTemp)) {
                continue;
            }
            
            Auth authItemTemp = AuthContext.getContext()
                    .getAuthItemFromContextById(authItemIdTemp);
            AssertUtils.notNull(authItemTemp,
                    "authItem is null.authItemId:{}",
                    authItemIdTemp);
            authItemRef.setAuthItem(authItemTemp);
            
            //添加进批量处理列表
            authItemRefList.add(authItemRef);
        }
        
        this.authItemRefImplDao.batchInsertAuthItemRefImpl(authItemRefList,
                tableSuffix);
    }
    
    /**
      * 批量插入某一权限引用类型的，一组引用id
      * <功能详细描述>
      * @param authRefType
      * @param authItemId
      * @param needInsertRefIds [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void batchInsertAuthItemRefByRefIds(String authRefType,
            String authItemId, List<String> needInsertRefIds, String systemId,
            String tableSuffix) {
        AssertUtils.notEmpty(systemId, "systemId is empty");
        List<AuthItemRef> authItemRefList = new ArrayList<AuthItemRef>();
        //跳过系统中不存在的权限
        if (!AuthContext.getContext()
                .getAllAuthItemMapping()
                .containsKey(authItemId)) {
            return;
        }
        //取得当前登录人员id
        Auth authItem = AuthContext.getContext()
                .getAuthItemFromContextById(authItemId);
        AssertUtils.notNull(authItem,
                "authItem is null.authItemId:{}",
                authItemId);
        
        String currentOperatorId = AuthSessionContext.getOperatorIdFromSession();
        for (String refIdTemp : needInsertRefIds) {
            AuthItemRef authItemRef = new AuthItemRef();
            authItemRef.setTemp(false);
            authItemRef.setCreateDate(new Date());
            authItemRef.setCreateOperId(currentOperatorId);
            authItemRef.setAuthItem(authItem);
            authItemRef.setAuthRefType(authRefType);
            authItemRef.setRefId(refIdTemp);
            
            //添加进批量处理列表
            authItemRefList.add(authItemRef);
        }
        
        this.authItemRefImplDao.batchInsertAuthItemRefImpl(authItemRefList,
                tableSuffix);
    }
    
    /**
      * 批量插入权限项引用到权限项引用历史表<br/>
      *<功能详细描述>
      * @param needInsertToHisAuthItemRefImpls
      * @param tableSuffix [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void batchInsertAuthItemRefToHis(
            List<AuthItemRef> needInsertToHisAuthItemRefImpls,
            String tableSuffix) {
        this.authItemRefImplDao.batchInsertAuthItemRefImplToHis(needInsertToHisAuthItemRefImpls,
                tableSuffix);
    }
}
