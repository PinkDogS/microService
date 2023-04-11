package com.ailen.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anshare.base.common.annotation.EnableRecursiveTrans;
import com.anshare.base.common.annotation.EnableTransDict;
import com.anshare.base.common.enums.ReturnTypeEnum;
import com.anshare.base.common.enums.TransPolicyEnum;
import com.anshare.base.common.feign.dto.BaseUserInfoDto;
import com.anshare.base.common.feign.dto.DictItemDto;
import com.anshare.base.common.feign.service.SystemFeignService;
import com.anshare.base.common.feign.service.UserFeignService;
import com.anshare.base.common.model.DictTransInfo;
import com.anshare.base.common.model.ListWrapper;
import com.anshare.base.common.model.Result;
import com.anshare.base.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.anshare.base.common.enums.ReturnTypeEnum.OBJECT;
import static com.anshare.base.common.enums.ReturnTypeEnum.TEXT;
import static com.anshare.base.common.enums.TransPolicyEnum.SYS_DICT;
import static com.anshare.base.common.enums.TransPolicyEnum.USERID;


/**
 * 字典转换，切面处理类
 *
 */
@Aspect
@Component
@Slf4j
public class TransDictAspect {
    
    public static final String BASE_PACKAGE = "com.anshare";
    
    public static final String TEXT_POSTFIX = "_TEXT";
    
    public static final String OBJECT_POSTFIX = "_OBJECT";
    
    
    @Resource
    private UserFeignService userFeignService;
    
    @Resource
    private SystemFeignService systemFeignService;
    
    @Pointcut("execution(public * com.anshare..*.*Controller.*(..))")
    public void execService() {
    
    }
    
    @Around("execService()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long stage0 = System.currentTimeMillis();
        if (result instanceof Result) {
            Result<Object> resultT = (Result<Object>) result;
            Object data = resultT.getData();
            if (data != null) {
                Map<TransPolicyEnum, DictTransInfo> dictInfoMap = new HashMap<>();
                // 提取字典信息
                extractDictField(data, dictInfoMap);
                long stage1 = System.currentTimeMillis();
    
                // 字典翻译
                doTransDict(dictInfoMap);
                long stage2 = System.currentTimeMillis();
    
                // 填充内容
                Object objTrans = fillDictInfo(data, dictInfoMap);
                long stage3 = System.currentTimeMillis();
    
                log.info("数据翻译耗时 {} ms", stage3 - stage0);
                resultT.setData(objTrans);
                return resultT;
            }
        }
    
        return result;
    }
    
    /**
     * 提取待翻译的字段信息
     * @author jimin
     */
    void extractDictField(Object data, Map<TransPolicyEnum, DictTransInfo> dictInfoMap) {
        if (data == null) {
            return;
        }
        
        if (dictInfoMap == null) {
            dictInfoMap = new HashMap<>();
        }
        
        if (data instanceof ListWrapper) {
            List<?> dataList = ((ListWrapper<?>) data).getList();
            // ListWrapper类数据
            if (CollectionUtil.isNotEmpty(dataList)) {
                for (Object obj: dataList) {
                    extractEachRecord(obj, dictInfoMap);
                }
            }
        } else if (data instanceof List) {
            List<?> dataList = (List) data;;
            
            // List类数据
            if (CollectionUtil.isNotEmpty(dataList)) {
                for (Object obj: dataList) {
                    extractEachRecord(obj, dictInfoMap);
                }
            }
        } else if (data.getClass().getName().indexOf(BASE_PACKAGE) >= 0){
            // 对BASE_PACKAGE下的对象进行转换
            extractEachRecord(data, dictInfoMap);
        } else {
            log.debug("{} 非目标包 {} 对象", data.getClass().getName(), BASE_PACKAGE);
        }
    }
    
    /**
     * 提取单个对象的字典信息
     * @author jimin
     */
    void extractEachRecord(Object obj, Map<TransPolicyEnum, DictTransInfo> dictInfoMap) {
        Field[] fields = ReflectUtil.getFields(ClassUtil.getClass(obj));
        for (Field field: fields) {
            if (field.getAnnotation(EnableTransDict.class) != null) {
                EnableTransDict annotation = field.getAnnotation(EnableTransDict.class);
                TransPolicyEnum policy = annotation.policy();
                String dictCode = "";
                if (policy == USERID) {
                    dictCode = USERID.name();
                } else if(policy == TransPolicyEnum.SYS_DICT) {
                    dictCode = annotation.dictCode();
                } else {
                    // TODO 其他策略
                }
                
                if (StrUtil.isNotEmpty(dictCode)) {
                    // 当前字典信息
                    DictTransInfo dictInfo = dictInfoMap.computeIfAbsent(policy, key -> new DictTransInfo());
                    
                    ReturnTypeEnum returnType = annotation.resType();
                    Object val = ReflectUtil.getFieldValue(obj, field);
                    if (val != null) {
                        dictInfo.getDictCodeAndDataMap().computeIfAbsent(dictCode, key -> new HashMap<>());
                        
                        // 处理待转换的字典的值
                        if (policy == USERID) {
                            // Key统一按Long处理
                            if (returnType == TEXT) {
                                dictInfo.getDictCodeAndDataMap()
                                        .computeIfAbsent(dictCode, key -> new HashMap<>())
                                        .put(Long.parseLong(val.toString()), null);
                            } else if (returnType == OBJECT){
                                dictInfo.getDictCodeAndDataObjMap()
                                        .computeIfAbsent(dictCode, key -> new HashMap<>())
                                        .put(Long.parseLong(val.toString()), null);
                            }
                        } else if (policy == TransPolicyEnum.SYS_DICT) {
                            // key统一按String处理
                            dictInfo.getDictCodeAndDataMap()
                                    .computeIfAbsent(dictCode, key -> new HashMap<>())
                                    .put(val.toString(), null);
                        } else {
                            // TODO 其他策略
                        }
                    }
                }
            } else if (field.getAnnotation(EnableRecursiveTrans.class) != null) {
                Object val = ReflectUtil.getFieldValue(obj, field);
                extractDictField(val, dictInfoMap);
            }
        }
    }
    
    void doTransDict(Map<TransPolicyEnum, DictTransInfo> dictInfoMap) {
        if(CollectionUtil.isEmpty(dictInfoMap)) {
            return;
        }
        for (TransPolicyEnum key: dictInfoMap.keySet()) {
            switch (key) {
                case SYS_DICT:
                    // 根据字典编码和值查询字典
                    // 1 根据字典编码查询字典ID
                    
                    // 2 根据字典ID，字典值，查询字典项列表
                    List<DictItemDto> dictCodeAndValueList = new ArrayList<>();
                    
                    dictInfoMap.get(SYS_DICT)
                            .getDictCodeAndDataMap()
                            .forEach((k, v) -> v.keySet()
                                    .forEach(vk -> dictCodeAndValueList.add(new DictItemDto()
                                                    .setDictCode(k)
                                                    .setItemValue(vk.toString()))));
                    if (CollectionUtil.isNotEmpty(dictCodeAndValueList)) {
                        Result<List<DictItemDto>> result = systemFeignService.listByDictCodeAndItemValueList(dictCodeAndValueList);
                        if (Result.isSucceed(result)) {
                            log.info("查询字典信息成功，加载字典数据{}条", result.getData().size());
                            Map<String, Map<String, String>> map = result.getData().stream()
                                    .collect(Collectors.groupingBy(
                                            DictItemDto::getDictCode,
                                            Collectors.toMap(DictItemDto::getItemValue, DictItemDto::getItemText)));
                            map.forEach((code, vAndTMap) -> vAndTMap.forEach((v, t) ->
                                    dictInfoMap.get(SYS_DICT)
                                            .getDictCodeAndDataMap()
                                            .get(code)
                                            .put(v, t)));
                        }
                    }
                    break;
                case USERID:
                    // 根据用户ID查询用户名
                    List<Long> idForNameList = dictInfoMap.get(USERID)
                            .getDictCodeAndDataMap()
                            .getOrDefault(USERID.name(), new HashMap<>())
                            .keySet()
                            .stream()
                            .map(o -> (Long)o)
                            .collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(idForNameList)) {
                        Result<Map<Long, String>> result = userFeignService.mapUserIdAndNameByUserIdList(idForNameList);
                        if (Result.isSucceed(result)) {
                            log.info("查询用户名称成功，加载用户信息{}条", result.getData().size());
                            result.getData().forEach((id, name) -> {
                                dictInfoMap.get(USERID)
                                        .getDictCodeAndDataMap()
                                        .get(USERID.name())
                                        .put(id, name);
                            });
                        }
                    }
    
                    // 根据用户ID查询用户信息
                    List<Long> idForObjList = dictInfoMap.get(USERID)
                            .getDictCodeAndDataObjMap()
                            .getOrDefault(USERID.name(), new HashMap<>())
                            .keySet()
                            .stream()
                            .map(o -> (Long)o)
                            .collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(idForObjList)) {
                        Result<Map<Long, BaseUserInfoDto>> result = userFeignService.mapUserIdAndUserInfoByUserIdList(idForObjList);
                        if (Result.isSucceed(result)) {
                            log.info("查询用户对象成功，加载用户信息{}条", result.getData().size());
                            result.getData().forEach((id, info) -> {
                                dictInfoMap.get(USERID)
                                        .getDictCodeAndDataObjMap()
                                        .get(USERID.name())
                                        .put(id, info);
                            });
                        } else {
                            log.warn("查询用户对象失败");
                        }
                    }
                    break;
                default:
            }
        }
    }
    
    Object fillDictInfo(Object data, Map<TransPolicyEnum, DictTransInfo> dictInfoMap) {
        if (data == null) {
            return null;
        }
        
        if (CollectionUtil.isEmpty(dictInfoMap)) {
            return data;
        }
        if (data instanceof ListWrapper) {
            // ListWrapper类型
            ListWrapper<?> wrapper = ((ListWrapper) data);
            if (CollectionUtil.isNotEmpty(wrapper.getList())) {
                List<JSONObject> rtList = wrapper.getList()
                        .stream()
                        .map(i -> fillEachRecord(i, dictInfoMap))
                        .collect(Collectors.toList());
    
                return ListWrapper.<JSONObject>builder()
                        .list(rtList)
                        .total(wrapper.getTotal())
                        .build();
            } else {
                return data;
            }
        }
        if (data instanceof List) {
            // List类型
            List<?> list = (List)data;
            if (CollectionUtil.isNotEmpty(list)) {
                return list.stream()
                        .map(i -> fillEachRecord(i, dictInfoMap))
                        .collect(Collectors.toList());
            } else {
                return data;
            }
        }  else if (data.getClass().getName().indexOf(BASE_PACKAGE) >= 0){
            //  普通对象
            // 对BASE_PACKAGE下的对象进行转换
            return fillEachRecord(data, dictInfoMap);
        }
        return data;
    }
    
    /**
     * 对单个对象的填充字典信息
     * @author jimin
     */
    JSONObject fillEachRecord(Object obj, Map<TransPolicyEnum, DictTransInfo> dictInfoMap) {
        if (obj == null) {
            return null;
        }

        // Jackson 序列化时对Long之类的类型做了toString转换，此处采用fastjson序列化，需保持转换逻辑一致
        JSONObject jsonObject = JSON.parseObject(JsonUtil.toJsonAsJackson(obj));
        
        Field[] fields = ReflectUtil.getFields(ClassUtil.getClass(obj));
        for (Field field: fields) {
            if (field.getAnnotation(EnableTransDict.class) != null) {
                EnableTransDict annotation = field.getAnnotation(EnableTransDict.class);
                TransPolicyEnum policy = annotation.policy();
                ReturnTypeEnum returnType = annotation.resType();
                String fieldName = annotation.fieldName();
                if (StrUtil.isEmpty(fieldName)) {
                    String postfix = annotation.postfix();
                    if (StrUtil.isEmpty(postfix)) {
                        postfix = returnType == TEXT ? TEXT_POSTFIX : OBJECT_POSTFIX;
                    }
                    fieldName = field.getName() + postfix;
                }
                
                String dictCode = "";
                if (policy == USERID) {
                    dictCode = USERID.name();
                } else if(policy == TransPolicyEnum.SYS_DICT) {
                    dictCode = annotation.dictCode();
                } else {
                    // TODO 其他策略
                }
                
                if (StrUtil.isNotEmpty(dictCode)) {
//                    // 当前字典信息
//                    DictTransInfo dictInfo = dictInfoMap.computeIfAbsent(policy, key -> new DictTransInfo());
//
//                    ReturnTypeEnum returnType = annotation.resType();
                    Object val = ReflectUtil.getFieldValue(obj, field);
                    if (val != null) {
                        // 处理待转换的字典的值
                        if (policy == USERID) {
                            // Key统一按Long处理
                            if (returnType == TEXT) {
                                String valTrans = dictInfoMap
                                        .get(USERID)
                                        .getDictCodeAndDataMap()
                                        .get(USERID.name())
                                        .get(val);
                                jsonObject.put(fieldName, valTrans);
                            } else {
                                Object valTrans = dictInfoMap
                                        .get(USERID)
                                        .getDictCodeAndDataObjMap()
                                        .get(USERID.name())
                                        .get(val);
                                jsonObject.put(fieldName, valTrans);
                            }
                        } else if (policy == TransPolicyEnum.SYS_DICT) {
                            // key统一按String处理
                            String valTrans = dictInfoMap
                                    .get(SYS_DICT)
                                    .getDictCodeAndDataMap()
                                    .get(dictCode)
                                    .get(String.valueOf(val));
    
                            jsonObject.put(fieldName, valTrans);
                        } else {
                            // TODO 其他策略
                        }
                    }
                }
            } else if (field.getAnnotation(EnableRecursiveTrans.class) != null) {
                Object val = ReflectUtil.getFieldValue(obj, field);
                Object valTrans = fillDictInfo(val, dictInfoMap);
                jsonObject.put(field.getName(), valTrans);
            }
        }
        return jsonObject;
    }
}
