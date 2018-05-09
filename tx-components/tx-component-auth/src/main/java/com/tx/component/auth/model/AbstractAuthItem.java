/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.util.ObjectUtils;

/**
 * 权限项
 * 如果两个权限项的 id与authType相同，则被认为是同一个authitem
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-11-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractAuthItem implements Auth {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2870014183031122725L;
    
    /** 权限项唯一键key : 约定权限项目分割符为"_" 如权限为"wd_" 不同的权限项,id也不能重复 */
    @Id
    private String id;
    
    /** 引用id */
    private String refId;
    
    /** 引用类型 */
    private String refType;
    
    /** 父级权限id */
    private String parentId;
    
    /** 系统唯一id */
    private String module;
    
    /** 权限项名 */
    private String name;
    
    /** 权限项目描述 */
    private String remark;
    
    /** 权限类型 后续根据需要可以扩展相应的权限大类   比如: 产品权限\流程环节权限\通过多个纬度的权限交叉可以达到多纬度的授权体系*/
    private String authType;
    
    /** 是否有效，默认为true,权限可停用 */
    private boolean valid = true;
    
    /** 是否可配置 */
    private boolean configAble = true;
    
    /** 是否可见 */
    private boolean viewAble = true;
    
    /** 是否可编辑 */
    private boolean editAble = false;
    
    /** 是否是虚拟权限，即不是真正的权限项 */
    private boolean virtual = false;
    
    /** 属性值 */
    private String attributes;
    
    /** 子权限列表 */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Auth> childs = new ArrayList<Auth>();
    
    /** <默认构造函数> */
    public AbstractAuthItem() {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public AbstractAuthItem(Map<String, Object> authItemRowMap) {
        super();
        if (authItemRowMap.containsKey("id")) {
            this.id = (String) authItemRowMap.get("id");
        }
        if (authItemRowMap.containsKey("parentId")) {
            this.parentId = (String) authItemRowMap.get("parentId");
        }
        if (authItemRowMap.containsKey("module")) {
            this.module = (String) authItemRowMap.get("module");
        }
        if (authItemRowMap.containsKey("name")) {
            this.name = (String) authItemRowMap.get("name");
        }
        if (authItemRowMap.containsKey("remark")) {
            this.remark = (String) authItemRowMap.get("remark");
        }
        if (authItemRowMap.containsKey("authType")) {
            this.authType = (String) authItemRowMap.get("authType");
        }
        if (authItemRowMap.containsKey(valid)) {
            this.valid = (boolean) authItemRowMap.containsKey("valid");
        }
        if (authItemRowMap.containsKey(configAble)) {
            this.configAble = (boolean) authItemRowMap
                    .containsKey("configAble");
        }
        if (authItemRowMap.containsKey(viewAble)) {
            this.viewAble = (boolean) authItemRowMap.containsKey("viewAble");
        }
        if (authItemRowMap.containsKey(editAble)) {
            this.editAble = (boolean) authItemRowMap.containsKey("editAble");
        }
    }
    
    /**
     * <默认构造函数>
     */
    public AbstractAuthItem(Auth otherAuthItem) {
        super();
        this.id = otherAuthItem.getId();
        this.parentId = otherAuthItem.getParentId();
        this.module = otherAuthItem.getModule();
        this.name = otherAuthItem.getName();
        this.remark = otherAuthItem.getRemark();
        this.authType = otherAuthItem.getAuthType();
        this.valid = otherAuthItem.isValid();
        this.configAble = otherAuthItem.isConfigAble();
        this.viewAble = otherAuthItem.isViewAble();
        this.editAble = otherAuthItem.isEditAble();
    }
    
    /** <默认构造函数> */
    public AbstractAuthItem(String id, String module, String authType) {
        super();
        this.id = id;
        this.module = module;
        this.authType = authType;
    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return id;
    }
    
    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return
     */
    @Override
    public String getParentId() {
        return parentId;
    }
    
    /**
     * @return 返回 module
     */
    @Override
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    @Override
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        if (StringUtils.isEmpty(name)) {
            return id;
        }
        return name;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return 返回 remark
     */
    @Override
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return
     */
    @Override
    public List<Auth> getChilds() {
        return childs;
    }
    
    /**
     * @param childs
     */
    @Override
    public void setChilds(List<Auth> childs) {
        this.childs = childs;
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthType() {
        return authType;
    }
    
    /**
     * @param authType
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    /**
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    /**
     * @return 返回 configAble
     */
    public boolean isConfigAble() {
        return configAble;
    }
    
    /**
     * @param 对configAble进行赋值
     */
    public void setConfigAble(boolean configAble) {
        this.configAble = configAble;
    }
    
    /**
     * @return 返回 viewAble
     */
    public boolean isViewAble() {
        return viewAble;
    }
    
    /**
     * @param 对viewAble进行赋值
     */
    public void setViewAble(boolean viewAble) {
        this.viewAble = viewAble;
    }
    
    /**
     * @return 返回 editAble
     */
    public boolean isEditAble() {
        return editAble;
    }
    
    /**
     * @param 对editAble进行赋值
     */
    public void setEditAble(boolean editAble) {
        this.editAble = editAble;
    }
    
    /**
     * @return 返回 refId
     */
    public String getRefId() {
        return refId;
    }
    
    /**
     * @param 对refId进行赋值
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }
    
    /**
     * @return 返回 refType
     */
    public String getRefType() {
        return refType;
    }
    
    /**
     * @param 对refType进行赋值
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }
    
    /**
     * @return 返回 virtual
     */
    public boolean isVirtual() {
        return virtual;
    }
    
    /**
     * @param 对virtual进行赋值
     */
    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }
    
    
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return ObjectUtils.equals(this, obj, "systemId", "id", "authType");
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int resHashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "systemId",
                "id",
                "authType");
        return resHashCode;
    }
}
