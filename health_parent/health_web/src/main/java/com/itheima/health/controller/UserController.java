package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Description: No Description
 * User: Eric
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    /**
     * 获取登陆用户名
     * 原始版本  弃用
     */
    /*@GetMapping("/getLoginUsername")
    public Result getLoginUsername(){
        // 获取登陆用户的认证信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 登陆用户名
        String username = loginUser.getUsername();
        // 返回给前端
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
    }*/


    /**
    *@author ${xinpl}
    *@Description 获取登陆用户名 + 动态展示系统菜单
    *@Date 2020/7/6 9:58
    *@Param
    *@return
    **/
    @GetMapping("/getLoginUsername")
    public Result getLoginUsername(){
        // 获取登陆用户的认证信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 登陆用户名
        String username = loginUser.getUsername();

        //结果封装  set<map<String,Object>>
        Map<String,Object> result = new HashMap<>();
        LinkedHashSet<Map<String,Object>> set = new LinkedHashSet<>();
        //获取用户菜单信息
        set =userService.getMenusByUser(username);
        result.put("username",username);
        result.put("set",set);
        // 返回给前端
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,result);
    }

}
