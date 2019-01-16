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
@Plugin(name = "SessionIdPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "sid", "seid", "sessionid" })
public class SessionIdPatternConverter extends LogEventPatternConverter {
    
    /** 为空时输出字符 */
    private static final String NA = "[]";
    
    protected SessionIdPatternConverter(final String[] options) {
        super("SessionId", "sessionId");
    }
    
    /**
     * Obtains an instance of pattern converter.
     *
     * @param options options, may be null.
     * @return instance of pattern converter.
     */
    public static SessionIdPatternConverter newInstance(
            final String[] options) {
        return new SessionIdPatternConverter(options);
    }
    
    /**
     * @param event
     * @param toAppendTo
     */
    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra == null || ra.getSessionId() == null
                || ra.getSessionId().length() == 0) {
            toAppendTo.append(NA);
        } else {
            toAppendTo.append("[").append(ra.getSessionId()).append("]");
        }
    }
    
}
