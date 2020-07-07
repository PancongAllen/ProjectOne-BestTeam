package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;



    @PostMapping("/add")
    public Result add(@RequestBody Permission permission){
        permissionService.add(permission);
        return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    @GetMapping("/deleteById")
    public Result deleteById(int id){
        permissionService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
    }

    @GetMapping("/findById")
    public Result findById(int id){
        Permission permission =permissionService.findById(id);
        return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,permission);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Permission permission){
        permissionService.update(permission);
        return new Result(true,MessageConstant.UPDATE_PERMISSION_SUCCESS);
    }


    @GetMapping("/findAll")
    public Result findAll(){
        List<Permission> list = permissionService.findAll();
        return new Result(true, MessageConstant.GET_AUTHORITY_LIST_SUCCESS,list);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Permission> pageResult = permissionService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,pageResult);
    }
}
