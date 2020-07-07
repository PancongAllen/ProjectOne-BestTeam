package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {



    void deleteById(Integer id, Integer level);

    Menu findById(int id);

    void update(Menu menu);

    List<Menu> findAll();




    //分页
    PageResult<Menu> findPage(QueryPageBean queryPageBean);
    //2级菜单
    List<Menu> find2menu(Integer categoryParentId);
    //1级菜单重命名
    void reName1(Integer categoryId, String categoryName);
    //添加菜单
    void add(String menu);
    void add2(Integer categoryId, String categoryName);


}
