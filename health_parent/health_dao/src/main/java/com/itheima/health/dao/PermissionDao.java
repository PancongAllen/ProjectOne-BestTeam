package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Permission;

import java.util.List;

public interface PermissionDao {

    List<Permission> findAll();

    void add(Permission permission);

    int findCountByPermissionId(int id);

    void deleteById(int id);

    Permission findById(int id);

    void update(Permission permission);

    List<Permission> findByCondition2(String queryString);


    /**
    *@author ${xinpl}
    *@Description 分页
    *@Date 2020/7/6 22:36
    *@Param [queryString]
    *@return com.github.pagehelper.Page<com.itheima.health.pojo.Permission>
    **/
    Page<Permission> selectByCondition(String queryString);
}
