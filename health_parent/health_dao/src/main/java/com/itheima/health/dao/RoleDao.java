package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author ${xinpl}
 * @date 2020/7/6-11:19
 */
public interface RoleDao {

    //根据角色 获取菜单 --> xpl
    List<Menu> getMenuByRoleIds(Integer rid);


    int deleteByPrimaryKey(Integer id);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    Page selectByCondition(String queryString);

    @Insert("insert into t_role_permission (role_id,permission_id) values (#{id},#{pid})")
    void setRoleAndPermission(Map<String, Integer> map);
    @Insert("insert into t_role_menu (role_id,menu_id) values (#{id},#{mid})")
    void setRoleAndMenu(Map<String, Integer> map);

    @Select("select menu_id from t_role_menu where role_id = #{id}")
    List<Integer> findMenuAndRoleById(int id);
    @Select("select permission_id from t_role_permission where role_id = #{id}")
    List<Integer> findPermissionAndRoleById(int id);

    @Delete("delete from t_role_menu where role_id = #{id}")
    void deleteRoleAndMenu(Integer id);
    @Delete("delete from t_role_permission where role_id = #{id}")
    void deleteRoleAndPermission(Integer id);
    @Select("select user_id from t_user_role where role_id = #{id}")
    List<Integer> findUserAndRoleById(int id);

    @Select("select * from t_role")
    List<Role> findAll();

}
