/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-11-5
 * <修改描述:>
 */
package com.tx.core.paged.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.tx.core.paged.PagedConstant;

/**
 * 分页对象<br/>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-11-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@JsonAutoDetect
public class PagedList<T> implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -1428503320236516181L;
    
    @SuppressWarnings("rawtypes")
    private List<?> list = new ArrayList();
    
    /** 默认每次查询的页数 */
    private int queryPageSize = PagedConstant.DEFAULT_QUERY_PAGE_SIZE;
    
    /** 每页显示条数 */
    private int pageSize = PagedConstant.DEFAULT_PAGE_SIZE;
    
    /** 当前页数 */
    private int pageIndex = PagedConstant.DEFAULT_PAGE_INDEX;
    
    /** 查询结果总条数 */
    private int count;
    
    /**
      * 返回总页数<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int getPageCount() {
        int pageCount = pageSize == 0 ? 0 : (count % pageSize == 0 ? count
                / pageSize : count / pageSize + 1);
        return pageCount;
    }
    
    /**
     * @return 返回 list
     */
    @SuppressWarnings("unchecked")
    public List<T> getList() {
        return (List<T>) list;
    }
    
    /**
     * @param 对list进行赋值
     */
    public void setList(List<?> list) {
        this.list = list;
    }
    
    /**
     * @return 返回 pageSize
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * @param 对pageSize进行赋值
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * @return 返回 pageIndex
     */
    public int getPageIndex() {
        return pageIndex;
    }
    
    /**
     * @param 对pageIndex进行赋值
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    
    /**
     * @return 返回 count
     */
    public int getCount() {
        return count;
    }
    
    /**
     * @param 对count进行赋值
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * @return 返回 queryPageSize
     */
    public int getQueryPageSize() {
        return queryPageSize;
    }
    
    /**
     * @param 对queryPageSize进行赋值
     */
    public void setQueryPageSize(int queryPageSize) {
        this.queryPageSize = queryPageSize;
    }
}
