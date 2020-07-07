package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.PermissionException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public Menu findById(int id) {
        return menuDao.findById(id);
    }

    @Override
    public void update(Menu menu) {
        menuDao.update(menu);
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    /*
    *@author ${xinpl}
    *@Description 分页
    *@Date 2020/7/7 0:05
    *@Param [pageBean]
    *@return com.itheima.health.entity.PageResult<com.itheima.health.pojo.Menu>
    **/
    @Override
    public PageResult<Menu> findPage(QueryPageBean pageBean) {
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        Page<Menu> page = menuDao.findPage(pageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }
    /*
     *@author ${xinpl}
     *@Description 获取2级菜单
     *@Date 2020/7/7 0:05
     *@Param [pageBean]
     *@return com.itheima.health.entity.PageResult<com.itheima.health.pojo.Menu>
     **/
    @Override
    public List<Menu> find2menu(Integer categoryParentId) {
        return menuDao.find2menu(categoryParentId);
    }
    /*
    *@author ${xinpl}
    *@Description 重命名
    *@Date 2020/7/7 0:21
    *@Param [categoryId, categoryName]
    *@return void
    **/
    @Override
    public void reName1(Integer categoryId, String categoryName) {
        menuDao.reName1(categoryId,categoryName);
    }

    /**
    *@author ${xinpl}
    *@Description 添加一级菜单
    *@Date 2020/7/7 2:01
    *@Param [menu]
    *@return void
    **/
    @Override
    public void add(String name) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setIcon("fa-users");
        menu.setLevel(1);
        //获取最大path
        Integer path = menuDao.getMaxPath()+1;
        menu.setPath(path+"");
        menuDao.add(menu);
    }

    /**
    *@author ${xinpl}
    *@Description 添加2级菜单
    *@Date 2020/7/7 2:20
    *@Param [categoryId, categoryName]
    *@return void
    **/
    @Override
    public void add2(Integer categoryId, String categoryName) {
        Menu menu = new Menu();
        menu.setName(categoryName);
        menu.setParentMenuId(categoryId);
        menu.setLevel(2);
        //获取priority 和 path
       Integer p = menuDao.getCountPriority(categoryId)+1;
       menu.setPriority(p);
       Integer path = menuDao.getPathById(categoryId);
       // path格式: /3-3
       String pathValue = "/"+path+"-"+p;
       menu.setPath(pathValue);
       menuDao.add(menu);
    }

    /**
    *@author ${xinpl}
    *@Description 删除菜单
    *@Date 2020/7/7 3:17
    *@Param [integer, id]
    *@return void
    **/
    @Override
    public void deleteById(Integer id, Integer level) {
        if(level == 1){
            //一级菜单判断是否有子菜单,有则不能删除
           int count = menuDao.getCountPriority(id);
           if(count > 0){
               throw new RuntimeException("存在子项,不能删除!");
           }
        }
        //二级菜单可以直接删除
        menuDao.deleteByPrimaryKey(id);

    }
}
