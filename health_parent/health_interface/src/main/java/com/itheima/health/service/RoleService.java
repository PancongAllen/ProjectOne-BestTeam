package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    PageResult findPage(QueryPageBean pageBean)throws Exception;

    void add(Role role, Integer[] permissionIds, Integer[] menuIds)throws Exception;

    void delete(int id)throws Exception;

    Role findById(int id)throws Exception;

    void update(Role role, Integer[] permissionIds, Integer[] menuIds)throws Exception;


    Map<String, Object> findAll()throws Exception;

    Map<String, List<Integer>> findPermissionAndMenuByRoleId(int id);
}
