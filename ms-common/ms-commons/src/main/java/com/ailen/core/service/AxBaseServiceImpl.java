package com.ailen.core.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.anshare.base.common.core.filter.BaseFilter;
import com.anshare.base.common.exception.AppAssert;
import com.anshare.base.common.exception.AppException;
import com.anshare.base.common.model.ListWrapper;
import com.anshare.base.common.model.UserInfo;
import com.anshare.base.common.utils.KeyUtil;
import com.anshare.base.common.utils.LongUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * service 实现基类
 *
 * @author jimin
 * @since 2023-02-07 20:09:06
 */
@Slf4j
public abstract class AxBaseServiceImpl<M extends BaseMapper<T>, T, F extends BaseFilter> extends ServiceImpl<M, T> implements AxBaseService<T, F> {
    /**
     * 默认配置
     * @author
     */
    private CurdConfig config = new CurdConfig();
    
    /**
     * 由子类覆盖
     * @author
     */
    public CurdConfig getCurdConfig() {
        return this.config;
    }
    
    private Class<T> modelClass;
    
    public Class<T> getModelClass() {
        if (modelClass == null) {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            // 获取T的类型
            modelClass = (Class<T>) pt.getActualTypeArguments()[1];
        }
        return modelClass;
    }
    
    @Override
    abstract public ListWrapper<? extends T> listByPage(F filter);
    
    protected ListWrapper<T> baseListByPage(Wrapper wrapper, Page page) {
        AppAssert.notNull(wrapper, "wrapper不得为空");
        if (page == null) {
            page = new Page();
        }

        IPage<T> pageList = this.page(page, wrapper);
        return ListWrapper.<T>builder()
                .list(pageList.getRecords())
                .total(pageList.getTotal())
                .build();
    }
    
    @Override
    public T findById(Long id) {
        if (LongUtil.isZero(id)) {
            return null;
        }
        return getById(id);
    }
    /**
     * 从 filter 提取 id列表，并更新filter对象
     * @author jimin
     */
    @Override
    public List<Long> parseIdList(F filter) {
        List<Long> idList = new ArrayList<>();
        if (LongUtil.isNotZero(filter.getId())) {
            idList.add(filter.getId());
        } else if(StrUtil.isNotEmpty(filter.getIds())) {
            idList.addAll(Arrays
                    .stream(StrUtil.splitToLong(filter.getIds(), ','))
                    .boxed()
                    .distinct()
                    .collect(Collectors.toList()));
        } else {
            idList = filter.getIdList();
        }
        // 同时更新filter对象
        filter.setIdList(idList);
        return idList;
    }
    
    @Override
    public T add(T model, UserInfo userInfo, CurdConfig config) {
        try {
            Field idField = Arrays.stream(getModelClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(TableId.class)).findFirst().orElse(null);
            if (idField == null) {
                idField = getModelClass().getDeclaredField(config.getIdField());
            }
            idField.setAccessible(true);
            Serializable id = (Serializable) idField.get(model);
            if (id == null) {
                // 未设置ID，尝试设置
                Class clz = idField.getType();
                if (Long.class.isAssignableFrom(clz)) {
                    idField.set(model, KeyUtil.getId());
                } else if (String.class.isAssignableFrom(clz)) {
                    idField.set(model, KeyUtil.getIdStr());
                }
            }
        } catch (Exception e) {
            log.warn(config.getIdField() + "字段设置异常，跳过", e);
        }
    
        try {
            Field createdByField = getModelClass().getDeclaredField(config.getCreatedByField());
            createdByField.setAccessible(true);
            
            Class clz = createdByField.getType();
            if (Long.class.isAssignableFrom(clz)) {
                createdByField.set(model, userInfo.getUserId());
            } else if (String.class.isAssignableFrom(clz)){
                createdByField.set(model, userInfo.getUserId().toString());
            }
        } catch (Exception e) {
            log.warn(config.getCreatedByField() + "字段设置异常，跳过", e);
        }
    
        try {
            Field createdTimeField = getModelClass().getDeclaredField(config.getCreatedTimeField());
            createdTimeField.setAccessible(true);
    
            Class clz = createdTimeField.getType();
            if (Date.class.isAssignableFrom(clz)) {
                createdTimeField.set(model, DateUtil.date());
            }
        } catch (Exception e) {
            log.warn(config.getCreatedTimeField() + "字段设置异常，跳过", e);
        }
        this.save(model);
        return model;
    }
    
    @Override
    public T add(T model, UserInfo userInfo) {
        return add(model, userInfo, getCurdConfig());
    }
    
    @Override
    public T update(T model, UserInfo userInfo, CurdConfig config) {
        Serializable id = null;
        try {
            Field idField = Arrays.stream(getModelClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(TableId.class)).findFirst().orElse(null);
            if (idField == null) {
                idField = getModelClass().getDeclaredField(config.getIdField());
            }
            idField.setAccessible(true);
            id = (Serializable) idField.get(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AppAssert.notNull(id, config.getIdField() + "不得为空");
    
        T old = this.getById(id);
        if (old != null) {
            BeanUtils.copyProperties(model, old);
    
            try {
                Field updatedByField = getModelClass().getDeclaredField(config.getUpdatedByField());
                updatedByField.setAccessible(true);
                updatedByField.set(old, userInfo.getUserId());
    
                Class clz = updatedByField.getType();
                if (Long.class.isAssignableFrom(clz)) {
                    updatedByField.set(model, userInfo.getUserId());
                } else if (String.class.isAssignableFrom(clz)){
                    updatedByField.set(model, userInfo.getUserId().toString());
                }
            } catch (Exception e) {
                log.warn(config.getUpdatedByField() + "字段设置异常，跳过", e);
            }
    
            try {
                Field updatedTimeField = getModelClass().getDeclaredField(config.getUpdatedTimeField());
                updatedTimeField.setAccessible(true);
    
                Class clz = updatedTimeField.getType();
                if (Date.class.isAssignableFrom(clz)) {
                    updatedTimeField.set(old, DateUtil.date());
                }
    
            } catch (Exception e) {
                log.warn(config.getUpdatedTimeField() + "字段设置异常，跳过", e);
            }
            
            this.updateById(old);
        }
        return old;
    }
    
    @Override
    public T update(T model, UserInfo userInfo) {
        return update(model, userInfo, getCurdConfig());
    }
    
    @Override
    public void delete(List<Long> idList, UserInfo userInfo) {
        this.delete(idList, userInfo, getCurdConfig().setDeleteLogical(true));
    }
    
    @Override
    public void delete(F filter, UserInfo userInfo) {
        this.delete(parseIdList(filter), userInfo, getCurdConfig().setDeleteLogical(true));
    }
    
    @Override
    public void deletePhysical(F filter, UserInfo userInfo) {
        this.delete(parseIdList(filter), userInfo, getCurdConfig().setDeleteLogical(false));
    }
    
    /**
     * 删除方法
     * @author jimin
     */
    private void delete(List<Long> idList, UserInfo userInfo, CurdConfig config) {
        if (CollectionUtil.isNotEmpty(idList)) {
            if (config.deleteLogical) {
                // 逻辑删除
                List<T> list = this.baseMapper.selectBatchIds(idList);
                if (CollectionUtil.isEmpty(list)) {
                    log.warn("指定ID的数据不存在，忽略");
                    return;
                }
                
                try {
                    Field isDeletedField = getModelClass().getDeclaredField(config.getIsDeletedField());
                    isDeletedField.setAccessible(true);
    
                    Field updatedByField = null;
                    try {
                        updatedByField = getModelClass().getDeclaredField(config.getUpdatedByField());
                        updatedByField.setAccessible(true);
                    } catch (Exception e) {
                        log.warn(config.getUpdatedByField() + "字段不存在，跳过");
                    }
    
                    Field updatedTimeField = null;
                    try {
                        updatedTimeField = getModelClass().getDeclaredField(config.getUpdatedTimeField());
                        updatedTimeField.setAccessible(true);
                    } catch (Exception e) {
                        log.warn(config.getUpdatedTimeField() + "字段不存在，跳过");
                    }
    
                    for(T i: list) {
                        // 设置删除标识，使用时间戳值，避免同名逻辑删除时唯一键冲突
                        isDeletedField.set(i, System.currentTimeMillis());
                        
                        // 设置其他字段
                        if (updatedByField != null) {
                            Class clz = updatedByField.getType();
                            if (Long.class.isAssignableFrom(clz)) {
                                updatedByField.set(i, userInfo.getUserId());
                            } else if (String.class.isAssignableFrom(clz)){
                                updatedByField.set(i, userInfo.getUserId().toString());
                            }
                        }
                        if (updatedTimeField != null) {
                            Class clz = updatedTimeField.getType();
                            if (Date.class.isAssignableFrom(clz)) {
                                updatedTimeField.set(i, DateUtil.date());
                            }
    
                        }
                    }
                    
                    // 更新数据集
                    this.updateBatchById(list);
                } catch (Exception e) {
                    log.error("系统异常", e);
                    throw new AppException("系统异常：" + e.getMessage());
                }
            } else {
                // 物理删除
                this.baseMapper.deleteBatchIds(idList);
            }
        }
    }
}
