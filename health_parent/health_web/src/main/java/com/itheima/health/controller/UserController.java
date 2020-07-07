package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @PostMapping("/add")
    public Result add(@RequestBody com.itheima.health.pojo.User user, Integer[] roleIds) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.add(user,roleIds);
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);
    }

    @PostMapping("/update")
    public Result update(@RequestBody com.itheima.health.pojo.User user,Integer[] roleIds) {
        userService.update(user,roleIds);
        return new Result(true, MessageConstant.UPDATE_USER_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<Role> role = userService.findAll();
        return new Result(true, MessageConstant.QUERY_USER_SUCCESS,role);
    }


    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用服务分页查询
        PageResult<com.itheima.health.pojo.User> pageResult = userService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_USER_SUCCESS, pageResult);
    }

    @RequestMapping("/findById")
    public Result findById(int id) {
        com.itheima.health.pojo.User user = userService.findById(id);
        return new Result(true, MessageConstant.QUERY_USER_SUCCESS, user);
    }

    @RequestMapping("/deleteById")
    public Result deleteById(int id) {
        userService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_MEMBER_SUCCESS);
    }

    @GetMapping("/findRoleByUserId")
    public Result findRoleByUserId(@RequestParam int id){
        List<Integer> roleIds = userService.findRoleByUserId(id);
        return new Result(true,MessageConstant.QUERY_ROLE_LIST_SUCCESS,roleIds);
    }

    //验证用户名
    @RequestMapping("/checkUsername")
    public Result checkUsername(String username){
        try {
            com.itheima.health.pojo.User user=userService.checkUsername(username);
            if (user!=null){
                return new Result(true, MessageConstant.GET_USERNAME_SUCCESS);

            }
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }


    @RequestMapping("/EditPassword")
    public Result EditPassword(@RequestParam Integer pid,@RequestParam String pass){
        try {
            userService.EditPassword(pid,bCryptPasswordEncoder.encode(pass));
            return new Result(true,"重置用户密码成功.");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"重置用户密码失败!");
        }
    }

}
