package com.example.crud.controller.base;

import com.example.crud.util.MessageResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
// import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @SuppressWarnings({"all"})
    private static Log log = LogFactory.getLog(BaseController.class);

    protected MessageResult success() { 
        return new MessageResult(0, "SUCCESS");
    }

    protected MessageResult success(String msg) {
        return new MessageResult(0, msg);
    }

    protected MessageResult success(String msg, Object obj) {
        MessageResult mr = new MessageResult(0, msg);
        mr.setData(obj);
        return mr;
    }

    protected MessageResult success(Object obj) {
        MessageResult mr = new MessageResult(0, "SUCCESS");
        mr.setData(obj);
        return mr;
    }

    protected MessageResult error(String msg) {
        return new MessageResult(500, msg);
    }

    protected MessageResult error(int code, String msg) {
        return new MessageResult(code, msg);
    }

    protected String request(HttpServletRequest request, String name) {
        // Removes control characters (char <= 32) from both ends of this String returning an empty String ("") if the String is empty ("") after the trim or if it is null.
        return StringUtils.trimToEmpty(request.getParameter(name));
    }
}
