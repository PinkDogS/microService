package com.ailen.exception;

import com.anshare.base.common.model.Result;
import com.anshare.base.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Object> exceptionHandler(Exception e) {
        String msg;
        //如果是业务逻辑异常，返回具体的错误码与提示信息
        if (e instanceof AppException) {
            AppException appException = (AppException) e;
            msg = appException.getMessage();
            try {
                // 得到异常棧的首个元素
                StackTraceElement stackTraceElement = e.getStackTrace()[0];
                String causeMsg = getMessage(e.getCause());
                if (stackTraceElement != null && StringUtil.isBlank(causeMsg)) {
                    log.error("系统错误：类名：{}，方法：{}，异常信息：{}", stackTraceElement.getClassName(),
                            stackTraceElement.getMethodName() + "---" + stackTraceElement.getLineNumber(),
                            appException.getMessage());
                } else {
                    log.error("系统错误：" + appException.getMessage() + " -- " + causeMsg);
                }

            } catch (Exception e1) {
                log.error("系统异常：{}", e1.getMessage());
            }
        } else if (e instanceof DuplicateKeyException) {
            // 统一唯一键冲突异常返回信息
            msg = "对象已存在";
        } else {
            // 其他系统异常
            msg = "系统异常" + e.getMessage();
            log.error("系统异常：{}", e.getMessage(), e);
        }
        return Result.failed(msg);
    }

    public static String getMessage(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Throwable) {
            StringWriter str = new StringWriter();
            PrintWriter pw = new PrintWriter(str);
            ((Throwable) obj).printStackTrace(pw);
            return str.toString();
            // return ((Throwable) obj).getMessage();
        } else {
            return obj.toString();
        }
    }

}
