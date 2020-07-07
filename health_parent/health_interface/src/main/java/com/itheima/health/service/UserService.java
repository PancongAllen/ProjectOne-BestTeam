package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户服务(企业员工)
 * User: Eric
 */
public interface UserService {
    /**
     * 根据登陆用户名称查询用户权限信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    //========================= xpl start ===========================//

    //动态获取菜单
    LinkedHashSet<Map<String, Object>> getMenusByUser(String username);

    //========================= xpl end =============================//

    void add(User user,Integer[] roleIds);

    void update(User user,Integer[] roleIds);


    List<Role> findAll();

    PageResult<User> findPage(QueryPageBean queryPageBean);

    User findById(int id);

    void deleteById(int id);


    List<Integer> findRoleByUserId(int userId);

    User checkUsername(String username);

    void EditPassword(Integer pid, String encode);
}
