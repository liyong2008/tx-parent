/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年1月10日
 * <修改描述:>
 */
package com.tx.core.support.log4j2.converter;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年1月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Plugin(name = "WebRequestSerialNumberPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "rsn", "wrsn", "requestsn", "webrequestsn" })
public class WebRequestSerialNumberPatternConverter
        extends LogEventPatternConverter {
    
    private static final String REQUEST_SERIAL_NUMBER_4_LOG = "REQUEST_SERIAL_NUMBER_4_LOG";
    
    private static final String DATE_FORMATTER = "yyMMddHHmmssSSS";
    
    private static final String MIN_DATE_FORMATTER = "ddHHmmssSSS";
    
    /** 为空时输出字符 */
    private static final String NA = "[]";
    
    /**
     * 生成请求流水号<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected static String generateRequestSerialNumber() {
        StringBuffer sb = new StringBuffer(32);
        sb.append(DateFormatUtils.format(new Date(), DATE_FORMATTER));
        sb.append("-");
        int hashCodeAbs = Math.abs(UUID.randomUUID().toString().hashCode());
        sb.append(String.format("%015d", hashCodeAbs));
        return sb.toString();
    }
    
    /**
     * 生成请求流水号<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected static String generateRequestShortSerialNumber() {
        StringBuffer sb = new StringBuffer(32);
        sb.append(DateFormatUtils.format(new Date(), MIN_DATE_FORMATTER));
        sb.append("-");
        int hashCodeAbs = Math.abs(UUID.randomUUID().toString().hashCode());
        sb.append(String.format("%08x", hashCodeAbs));
        return sb.toString();
    }
    
    protected WebRequestSerialNumberPatternConverter(final String[] options) {
        super("WebRequestSerialNumber", "webRequestSerialNumber");
    }
    
    /**
     * Obtains an instance of pattern converter.
     *
     * @param options options, may be null.
     * @return instance of pattern converter.
     */
    public static WebRequestSerialNumberPatternConverter newInstance(
            final String[] options) {
        return new WebRequestSerialNumberPatternConverter(options);
    }
    
    /**
     * @param event
     * @param toAppendTo
     */
    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra == null || ra.getAttribute(RequestAttributes.REFERENCE_REQUEST,
                RequestAttributes.SCOPE_REQUEST) == null) {
            toAppendTo.append(NA);
        } else {
            String requestSerialNumber = (String) ra.getAttribute(
                    REQUEST_SERIAL_NUMBER_4_LOG,
                    RequestAttributes.SCOPE_REQUEST);
            if (StringUtils.isEmpty(requestSerialNumber)) {
                requestSerialNumber = generateRequestShortSerialNumber();
                ra.setAttribute(REQUEST_SERIAL_NUMBER_4_LOG,
                        requestSerialNumber,
                        RequestAttributes.SCOPE_REQUEST);
            }
            toAppendTo.append("[").append(requestSerialNumber).append("]");
        }
    }
    
    //    public static void main(String[] args) {
    //        System.out.println(DateFormatUtils.format(new Date(), DATE_FORMATTER));
    //        System.out.println(DateFormatUtils.format(new Date(), DATE_FORMATTER));
    //        System.out.println(DateFormatUtils.format(new Date(), DATE_FORMATTER));
    //        System.out.println(DateFormatUtils.format(new Date(), DATE_FORMATTER));
    //        
    //        System.out.println(UUID.randomUUID().toString());
    //        System.out.println(UUID.randomUUID().toString().length());
    //        
    //        int hashCodeAbs = Math.abs(UUID.randomUUID().toString().hashCode());
    //        System.out.println(String.format("%015d", hashCodeAbs));
    //        
    //        System.out.println(generateRequestSerialNumber());
    //        System.out.println(generateRequestShortSerialNumber());
    //        
    //        System.out.println(String.format("%010d", Integer.MAX_VALUE));
    //        System.out.println(Integer.MAX_VALUE);
    //        
    //        System.out.println(String.format("%010x", Integer.MAX_VALUE));
    //        System.out.println(String.format("%08x", Integer.MAX_VALUE));
    //    }
    
}
