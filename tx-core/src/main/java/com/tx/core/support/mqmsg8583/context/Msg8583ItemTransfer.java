/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月6日
 * <修改描述:>
 */
package com.tx.core.support.mqmsg8583.context;

/**
 * 文本项转换器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface Msg8583ItemTransfer<T> {
    
    void setConfigurator(Msg8583TransferConfigurator configurator);
    
    String marshal(T parameter, int start, int length);
    
    T unmarshal(String sb, int start, int length);
}
