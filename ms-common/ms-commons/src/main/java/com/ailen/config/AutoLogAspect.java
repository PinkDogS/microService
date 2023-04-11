package com.ailen.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.anshare.base.common.annotation.EnableAutoLog;
import com.anshare.base.common.constant.CommonConstant;
import com.anshare.base.common.constant.SecurityConstants;
import com.anshare.base.common.dto.SysLogDto;
import com.anshare.base.common.feign.service.UserFeignService;
import com.anshare.base.common.model.Result;
import com.anshare.base.common.model.UserInfo;
import com.anshare.base.common.utils.IPUtil;
import com.anshare.base.common.utils.ServletUtils;
import com.anshare.base.common.vo.ReqParam;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 系统日志，切面处理类
 *
 * @Author scott
 * @email jeecgos@163.com
 * @Date 2018年1月14日
 */
@Aspect
@Component
public class AutoLogAspect {
    
    @Resource
    private UserFeignService userFeignService;
    
    @Pointcut("@annotation(com.anshare.base.common.annotation.EnableAutoLog)")
    public void logPointCut() {
    
    }
    
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        
        //保存日志
        saveSysLog(point, time, result);
        
        return result;
    }
    
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time, Object obj) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        SysLogDto dto = new SysLogDto();
        EnableAutoLog autoLog = method.getAnnotation(EnableAutoLog.class);
        if (autoLog != null) {
            String content = autoLog.value();
            //注解上的描述,操作日志内容
            dto.setLogType(autoLog.logType());
            dto.setLogContent(content);
        }
        
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        dto.setMethod(className + "." + methodName + "()");
        
        
        //设置操作类型
        if (dto.getLogType() == CommonConstant.LOG_TYPE_OPERATE) {
            dto.setOperateType(getOperateType(methodName, autoLog.operateType()));
        }
        
        //获取request
        HttpServletRequest request = ServletUtils.getRequest();
        //请求的参数
        dto.setRequestParam(getReqestParams(request, joinPoint));
        dto.setRequestUrl(StrUtil.subWithLength(ServletUtils.getRequest().getRequestURI(), 0, 255));
        dto.setRequestType(request.getMethod());
        //设置IP地址
        dto.setIp(IPUtil.getIpAddr(request));
        //获取登录用户信息
        String userId = request.getHeader(SecurityConstants.USER_ID_HEADER);
        if (StrUtil.isNotEmpty(userId) && !userId.equals("null")) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(Long.parseLong(userId));
            Result<UserInfo> userInfoResult = userFeignService.findLoginUser(Long.parseLong(userId));
            if (Result.isSucceed(userInfoResult)) {
                userInfo = userInfoResult.getData();
            }
            dto.setUserid(userInfo.getUserId());
            dto.setLoginid(userInfo.getLoginId());
            dto.setRealname(userInfo.getUserName());
        }
    
        //耗时
        dto.setCostTime(time);
        dto.setCreateTime(DateUtil.date());
        dto.setLogDate(DateUtil.today());
        
        //保存系统日志
        userFeignService.saveSysLog(dto);
    }
    
    
    /**
     * 获取操作类型
     */
    private int getOperateType(String methodName, int operateType) {
        if (operateType > 0) {
            return operateType;
        }
        String lowerCaseMethodName = methodName.toLowerCase(Locale.ROOT);
        
        if (lowerCaseMethodName.indexOf("list") >= 0 || lowerCaseMethodName.indexOf("page") >= 0) {
            return CommonConstant.OP_TYPE_QRY;
        }
        if (lowerCaseMethodName.indexOf("add") >= 0 || lowerCaseMethodName.indexOf("save") >= 0) {
            return CommonConstant.OP_TYPE_ADD;
        }
        if (lowerCaseMethodName.indexOf("edit") >= 0 || lowerCaseMethodName.indexOf("update") >= 0) {
            return CommonConstant.OP_TYPE_MOD;
        }
        if (lowerCaseMethodName.indexOf("delete") >= 0 || lowerCaseMethodName.indexOf("del") >= 0) {
            return CommonConstant.OP_TYPE_DEL;
        }
        if (lowerCaseMethodName.indexOf("import") >= 0) {
            return CommonConstant.OP_TYPE_IMPORT;
        }
        if (lowerCaseMethodName.indexOf("export") >= 0) {
            return CommonConstant.OP_TYPE_EXPORT;
        }
        return CommonConstant.OP_TYPE_OTHER;
    }
    
    /**
     * @param request:   request
     * @param joinPoint: joinPoint
     * @Description: 获取请求参数
     */
    private String getReqestParams(HttpServletRequest request, JoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        String params = "";
        if ("POST".equals(httpMethod) || "PUT".equals(httpMethod) || "PATCH".equals(httpMethod)) {
            Object[] paramsArray = joinPoint.getArgs();
            // java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
            //  https://my.oschina.net/mengzhang6/blog/2395893
            List<Object> arguments = new ArrayList<>();
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof BindingResult || paramsArray[i] instanceof ServletRequest || paramsArray[i] instanceof ServletResponse || paramsArray[i] instanceof MultipartFile) {
                    //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                    //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                    continue;
                }
    
                // 排除自定义注解注入的参数
                if (paramsArray[i] instanceof UserInfo || paramsArray[i] instanceof ReqParam) {
                    continue;
                }
                
                arguments.add(paramsArray[i]);
            }
            // 超过2000字符丢弃
            PropertyFilter profilter = (o, name, value) -> {
                if (value != null && value.toString().length() > 2000) {
                    return false;
                }
                return true;
            };
            
            params = JSONObject.toJSONString(arguments, profilter);
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 请求的方法参数值
            Object[] args = joinPoint.getArgs();
            // 请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paramNames = u.getParameterNames(method);
            if (args != null && paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params += "  " + paramNames[i] + ": " + args[i];
                }
            }
        }
        return params;
    }
}
