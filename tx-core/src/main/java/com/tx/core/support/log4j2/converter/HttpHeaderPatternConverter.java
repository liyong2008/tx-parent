/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年1月10日
 * <修改描述:>
 */
package com.tx.core.support.log4j2.converter;

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
@Plugin(name = "CookiePatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "cookie" })
public class HttpHeaderPatternConverter extends LogEventPatternConverter {
    
    /** 为空时输出字符 */
    private static final String NA = "[]";
    
    protected HttpHeaderPatternConverter(final String[] options) {
        super("Cookie", "cookie");
    }
    
    /**
     * Obtains an instance of pattern converter.
     *
     * @param options options, may be null.
     * @return instance of pattern converter.
     */
    public static HttpHeaderPatternConverter newInstance(final String[] options) {
        return new HttpHeaderPatternConverter(options);
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
//            String requestSerialNumber = (String) ra.getAttribute(
//                    REQUEST_SERIAL_NUMBER_4_LOG,
//                    RequestAttributes.SCOPE_REQUEST);
//            if (StringUtils.isEmpty(requestSerialNumber)) {
//                requestSerialNumber = generateRequestShortSerialNumber();
//                ra.setAttribute(REQUEST_SERIAL_NUMBER_4_LOG,
//                        requestSerialNumber,
//                        RequestAttributes.SCOPE_REQUEST);
//            }
//            toAppendTo.append("[").append(requestSerialNumber).append("]");
        }
    }
    
}
