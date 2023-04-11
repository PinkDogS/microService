package com.ailen.core.controller;

import com.anshare.base.common.annotation.CommonReqParam;
import com.anshare.base.common.annotation.LoginUser;
import com.anshare.base.common.core.filter.BaseFilter;
import com.anshare.base.common.core.service.AxBaseService;
import com.anshare.base.common.feign.service.UserFeignService;
import com.anshare.base.common.model.ListWrapper;
import com.anshare.base.common.model.Result;
import com.anshare.base.common.model.UserInfo;
import com.anshare.base.common.vo.ReqParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * controller 基类
 *
 * @author jimin
 * @since 2023-02-07 20:07:20
 */
public abstract class AxBaseController<T, F extends BaseFilter, S extends AxBaseService<T, F>> {
    @Autowired
    protected S service;
    
    @Resource
    UserFeignService userFeignService;
    
    @ApiOperation("分页查询")
    @PostMapping(value = "/listByPage")
    public Result<ListWrapper<? extends T>> listByPage (@RequestBody F filter) {
        return Result.succeed(service.listByPage(filter));
    }
    
    @ApiOperation(value = "添加")
    @PostMapping(value = "/add")
    public Result add(@RequestBody T model, @LoginUser UserInfo userInfo, @CommonReqParam ReqParam reqParam) {
        T entity = service.add(model, userInfo);
        return Result.succeed(entity);
    }
    
    @ApiOperation(value = "修改")
    @PostMapping(value = "/update")
    public Result update(@RequestBody T model, @LoginUser UserInfo userInfo) {
        T entity = service.update(model, userInfo);
        return Result.succeed(entity);
    }
    
    @ApiOperation(value = "删除(逻辑)")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody F filter, @LoginUser UserInfo userInfo) {
        service.delete(filter, userInfo);
        return Result.succeed();
    }
    
    public List<Long> parseIdList(F filter) {
        return service.parseIdList(filter);
    }
    
    @ApiOperation(value = "根据ID查询单个对象")
    @GetMapping(value = "/detail")
    public Result findById(@RequestParam("id") Long id) {
        return Result.succeed(service.findById(id));
    }
    
}
