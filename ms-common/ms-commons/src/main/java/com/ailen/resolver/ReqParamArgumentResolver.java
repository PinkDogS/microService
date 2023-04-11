package com.ailen.resolver;

import cn.hutool.core.util.StrUtil;
import com.anshare.base.common.annotation.CommonReqParam;
import com.anshare.base.common.constant.SecurityConstants;
import com.anshare.base.common.utils.JsonUtil;
import com.anshare.base.common.vo.ReqParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * json转换为ReqParam
 *
 * @author anshare
 * @version 1.0
 * 
 **/
@Slf4j
public class ReqParamArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CommonReqParam.class) && parameter.getParameterType().equals(ReqParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String commonReqParams = request.getHeader(SecurityConstants.COMMON_PARAM_HEADER);
        if (StrUtil.isBlank(commonReqParams)) {
            log.error("resolveArgument error commonReqParams is empty");
        }
        ReqParam reqParam = JsonUtil.parseObject(commonReqParams, ReqParam.class);
        if(reqParam==null){
            reqParam =new ReqParam();
        }
        if (reqParam.getCorpId() == null) {
            reqParam.setCorpId(0L);
        }
        return reqParam;
    }
}
