package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;


    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Menu> pageResult = menuService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_MENU_LIST_SUCCESS,pageResult);
    }
    @GetMapping("/findById")
    public Result findById(int id){
        Menu menu = menuService.findById(id);
        return new Result(true,MessageConstant.QUERY_MENU_BY_ID_SUCCESS,menu);
    }
    @PostMapping("/update")
    public Result update(@RequestBody Menu menu){
        menuService.update(menu);
        return new Result(true,MessageConstant.UPDATE_MENU_SUCCESS);
    }
    @GetMapping("/findAll")
    public Result findAll(){
        List<Menu> menus =  menuService.findAll();
        return new Result(true,MessageConstant.QUERY_MENUS_SUCCESS,menus);
    }


    /**
    *@author ${xinpl}
    *@Description 获取2级菜单
    *@Date 2020/7/7 0:01
    *@Param categoryParentId
    *@return
    **/
    @RequestMapping("/find2menu")
    public Result find2menu(@RequestParam Integer categoryParentId){
        List<Menu> menus = menuService.find2menu(categoryParentId);
        return new Result(true,"200",menus);
    }

    /**
    *@author ${xinpl}
    *@Description 一级菜单编辑
    *@Date 2020/7/7 0:20
    *@Param [categoryId, categoryName]
    *@return com.itheima.health.entity.Result
    **/
    @RequestMapping("/reName1")
    public Result reName1(Integer categoryId,String categoryName){
        menuService.reName1(categoryId,categoryName);
        return new Result(true,"200");
    }

    /**
    *@author ${xinpl}
    *@Description 添加一级菜单
    *@Date 2020/7/7 2:00
    *@Param [name]
    *@return com.itheima.health.entity.Result
    **/
    @PostMapping("/add")
    public Result add(@RequestParam String name){
        menuService.add(name);
        return new Result(true,MessageConstant.ADD_MENU_SUCCESS);
    }

    /**
    *@author ${xinpl}
    *@Description 添加2级菜单
    *@Date 2020/7/7 2:18
    *@Param [name]
    *@return com.itheima.health.entity.Result
    **/
    @PostMapping("/add2")
    public Result add2(@RequestParam Integer categoryId,@RequestParam String categoryName){
        menuService.add2(categoryId,categoryName);
        return new Result(true,MessageConstant.ADD_MENU_SUCCESS);
    }

    @PostMapping("/deleteById")
    public Result deleteById(@RequestParam Integer id,@RequestParam Integer level){
        menuService.deleteById(id,level);
        return new Result(true,"删除成功");
    }

}
