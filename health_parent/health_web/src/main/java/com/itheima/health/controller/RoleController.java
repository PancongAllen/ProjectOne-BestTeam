package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ${xinpl}
 * @date 2020/7/7-3:40
 */
@RestController
@RequestMapping("/Role")
public class RoleController {

    @Reference
    private RoleService roleService;

    /**
     *@author ${xinpl}
     *@Description 菜单数据分页
     *@Param [queryPageBean]
     *@return com.itheima.constant.Result
     **/
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = roleService.findPage(queryPageBean);
            return new Result(true,"成功",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"菜单分页失败!");
        }
    }


    /**
     *@author ${xinpl}
     *@Description 新增role数据
     *@Param [role, permissionIds, menuIds]
     *@return com.itheima.constant.Result
     **/
    @RequestMapping("/add")
    public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds){
        try {
            roleService.add(role,permissionIds,menuIds);
            return new Result(true,"新增角色成功.");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"新增角色失败!");
        }

    }


    /**
     *@author ${xinpl}
     *@Description 回显权限列表 和 菜单列表初始数据
     *@return com.itheima.constant.Result
     **/
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            Map<String,Object> map = roleService.findAll();
            return new Result(true,"查询菜单成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询菜单失败");
        }
    }

    /**
     *@author ${xinpl}
     *@Description 删除角色
     **/
    @RequestMapping("/delete")
    public Result delete(int id){
        try {
            roleService.delete(id);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"此角色与权限或者菜单关联,不能删除!");
        }
    }

    /**
     *@author ${xinpl}
     *@Description 角色修改
     **/
    @RequestMapping("/update")
    public Result update(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds){
        try {
            roleService.update(role,permissionIds,menuIds);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改角色失败!");
        }
    }

    /**
     *@author ${xinpl}
     *@Description 回显role数据
     **/
    @RequestMapping("/findById")
    public Result findById(int id){
        try {
            Role role = roleService.findById(id);
            return new Result(true,"数据加载成功",role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"角色回显数据失败!");
        }
    }

    /**
     *@author ${xinpl}
     *@Description 根据roleid 返回拥有的权限和菜单
     **/
    @RequestMapping("/findPermissionAndMenuByRoleId")
    public Result findPermissionAndMenuByRoleId(int id){
        try {
            Map<String, List<Integer>> map = new HashMap<>();
            map = roleService.findPermissionAndMenuByRoleId(id);
            return new Result(true,"数据加载成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"角色回显数据失败!");
        }
    }

}