package com.itheima.health.dao;

import com.itheima.health.pojo.Menu;

import java.util.List;

/**
 * @author ${xinpl}
 * @date 2020/7/6-11:19
 */
public interface RoleDao {

    //根据角色 获取菜单 --> xpl
    List<Menu> getMenuByRoleIds(Integer rid);
}
