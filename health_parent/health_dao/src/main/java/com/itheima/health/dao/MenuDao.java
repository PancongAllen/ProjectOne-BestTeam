package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuDao {

    List<Menu> findByCondition2(String queryString);

    void add(Menu menu);

    int findCountByPermissionId(Integer id);

    void deleteById(Integer id);

    Menu findById(int id);

    void update(Menu menu);

    List<Menu> findAll();




    Page<Menu> findPage(String queryString);

    List<Menu> find2menu(Integer categoryParentId);

    void reName1(@Param("cid") Integer categoryId, @Param("cname") String categoryName);

    @Select("select max(CONVERT(path,signed)) from t_menu where level =1 ")
    Integer getMaxPath();
    //获取优先权
    @Select("select count(1) from t_menu where level =2 and parentMenuId = #{categoryId}")
    Integer getCountPriority(Integer categoryId);

    //获取path 用来拼接
    @Select("select path from t_menu where id = #{categoryId}")
    Integer getPathById(Integer categoryId);

    @Delete("delete from t_menu where id  = #{id}")
    void deleteByPrimaryKey(Integer id);

    @Select("select count(1) from t_role_menu where menu_id = #{id}")
    int countMenuAndRole(Integer id);
}
