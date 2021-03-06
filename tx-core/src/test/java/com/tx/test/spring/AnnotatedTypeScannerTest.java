/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.test.spring;

import org.springframework.data.util.AnnotatedTypeScanner;

import com.tx.core.mybatis.annotation.AutoPersistEntitySupport;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AnnotatedTypeScannerTest {
    
    public static void main(String[] args) {
        AnnotatedTypeScanner scanner = new AnnotatedTypeScanner(AutoPersistEntitySupport.class);
        
        System.out.println(scanner.findTypes("com.tx").size());
    }
}
