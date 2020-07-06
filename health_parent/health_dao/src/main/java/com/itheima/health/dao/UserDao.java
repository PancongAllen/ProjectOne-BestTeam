package com.itheima.health.dao;

import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Description: No Description
 * User: Eric
 */
public interface UserDao {
    /**
     * 根据登陆用户名称查询用户权限信息
     * @param username
     * @return
     */
    User findByUsername(String username);


    /**
    *@author ${xinpl}
    *@Description 根据用户名 获取用户所有角色id
    *@Date 2020/7/6 11:20
    *@Param [username]
    *@return java.util.List<java.lang.Integer>
    **/
    @Select("select ur.role_id from t_user u ,t_user_role ur where u.id = ur.user_id and u.username = #{username}")
    List<Integer> getRoleByUsername(String username);
}
