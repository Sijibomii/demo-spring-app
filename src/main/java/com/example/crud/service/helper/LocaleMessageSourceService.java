package com.example.crud.service.helper;

import javax.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.util.Locale;


@Component
public class LocaleMessageSourceService {

    @Resource
    private MessageSource messageSource;

    /**
     * @param code ：The key corresponding to the messages configuration.
     * @return
     */
    public String getMessage(String code){
        return getMessage(code,null);
    }

    /**
     *
     * @param code ：The key corresponding to the messages configuration.
     * @param args : array parameter.
     * @return
     */
    public String getMessage(String code,Object[] args){
        return getMessage(code, args,"");
    }


    /**
     *
     * @param code ：The key corresponding to the messages configuration.
     * @param args :array parameter.
     * @param defaultMessage : The default value when no key is set.
     * @return
     */
    public String getMessage(String code,Object[] args,String defaultMessage){
        // https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

}

