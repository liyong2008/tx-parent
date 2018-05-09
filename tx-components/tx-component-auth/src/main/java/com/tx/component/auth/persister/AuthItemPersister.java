/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.component.auth.persister;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.tx.component.auth.context.AuthTypeItemContext;
import com.tx.component.auth.model.Auth;
import com.tx.component.auth.model.AuthItem;
import com.tx.component.auth.service.AuthItemService;

/**
 * 权限加载器，从DB中进行加载<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-4-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthItemPersister{
    
    private String tableSuffix;
    
    private String systemId;
    
    @Resource(name = "authItemImplService")
    private AuthItemService authItemImplService;
    
    /** <默认构造函数> */
    public AuthItemPersister() {
        super();
    }
    
    /** <默认构造函数> */
    public AuthItemPersister(String tableSuffix, String systemId) {
        super();
        this.tableSuffix = tableSuffix;
        this.systemId = systemId;
    }

    /** <默认构造函数> */
    public AuthItemPersister(String tableSuffix, String systemId,
            AuthItemService authItemService) {
        super();
        this.tableSuffix = tableSuffix;
        this.systemId = systemId;
        this.authItemImplService = authItemService;
    }
    
    /**
     * @param authItemId
     * @return
     */
    public Auth findAuthItem(String authItemId) {
        Auth authItem = this.authItemImplService.findAuthItemImplById(authItemId,
                systemId,
                tableSuffix);
        return authItem;
    }
    
    /**
     * 从数据库中加载权限项
     * @return
     */
    public Set<Auth> listAuthItem() {
        Set<Auth> resSet = new HashSet<Auth>();
        List<AuthItem> authItemImplList = this.authItemImplService.queryAllAuthItemListBySystemId(systemId,
                tableSuffix);
        if (authItemImplList == null) {
            return resSet;
        }
        
        for (AuthItem authItemTemp : authItemImplList) {
            AuthTypeItemContext.getContext()
                    .registeAuthTypeItem(authItemTemp.getAuthType());
            resSet.add(authItemTemp);
        }
        return resSet;
    }
    
    /**
     * @param 对tableSuffix进行赋值
     */
    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }
    
    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    /**
     * @param 对authItemService进行赋值
     */
    public void setAuthItemService(AuthItemService authItemService) {
        this.authItemImplService = authItemService;
    }
}
