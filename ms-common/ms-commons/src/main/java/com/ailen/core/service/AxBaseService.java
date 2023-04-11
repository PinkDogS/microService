package com.ailen.core.service;

import com.anshare.base.common.core.filter.BaseFilter;
import com.anshare.base.common.model.ListWrapper;
import com.anshare.base.common.model.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * service 接口基类
 *
 * @author jimin
 * @since 2023-02-07 20:07:56
 */
public interface AxBaseService<T, F extends BaseFilter> extends IService<T> {
    /**
     * 分页查询
     * @author
     */
    ListWrapper<? extends T> listByPage(F filter);
    
    /**
     * 根据id查询单个对象
     * @author jimin
     */
    T findById(Long id);
    
    /**
     * 从filter中提取id列表
     * @author jimin
     */
    List<Long> parseIdList(F filter);
    
    /**
     * 根据配置添加对象
     * @author jimin
     */
    T add(T model, UserInfo userInfo, CurdConfig config);
    
    /**
     * 添加
     * @author
     */
    T add(T model, UserInfo userInfo);
    
    /**
     * 从filter中提取id列表
     * @author jimin
     */
    T update(T model, UserInfo userInfo, CurdConfig config);
    
    /**
     * 通过默认配置修改
     * @author
     */
    T update(T model, UserInfo userInfo);
    
    /**
     * 根据id列表逻辑删除对象
     * @author jimin
     */
    void delete(List<Long> idList, UserInfo userInfo);
    
    /**
     * 根据filter对象逻辑删除
     * @author
     */
    void delete(F filter, UserInfo userInfo);
    
    /**
     * 物理删除
     * @author
     */
    void deletePhysical(F filter, UserInfo userInfo);
}
