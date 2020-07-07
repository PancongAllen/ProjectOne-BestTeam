package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    //curd
    int add(User user);
    @Delete("delete from t_user where id = #{id}")
    int deleteByPrimaryKey(Integer id);
    @Select("select * from t_user where id=#{id}")
    User selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(User user);

    //分页
    Page<User> findPage(String queryString);












    @Delete("delete from t_user_role where user_id = #{id}")
    void deleteRoleAndUser(Integer id);

    @Insert("insert into t_user_role (role_id,user_id) values (#{rid},#{id}) ")
    void setRoleAndUser(Map<String, Integer> map);

    //回显角色
    @Select("select role_id from t_user_role where user_id = #{id} ")
    List<Integer> findRoleByUserId(int id);

    @Update("UPDATE t_user set password = #{pass} where id = #{pid} ")
    void EditPassword(@Param("pid") Integer pid, @Param("pass") String pass);


    //校验用户名
    @Select("select * from t_user where username = #{username}")
    User checkUsername(String username);
}
