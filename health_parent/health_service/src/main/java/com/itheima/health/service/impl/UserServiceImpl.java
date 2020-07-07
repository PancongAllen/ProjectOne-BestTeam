package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.PermissionException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Description: No Description
 * User: Eric
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 根据登陆用户名称查询用户权限信息
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }


    /**
    *@author ${xinpl}
    *@Description 动态获取菜单
    *@Date 2020/7/6 10:22
    *@Param username
    *@return LinkedHashSet
    **/
    @Override
    public LinkedHashSet<Map<String, Object>> getMenusByUser(String username) {
        //用set防止角色,菜单重复
        LinkedHashSet<Map<String, Object>> set = new LinkedHashSet<>();
        //根据用户名 获取用户所有角色id
        List<Integer> roleIds = userDao.getRoleByUsername(username);
        //遍历角色 获取菜单 菜单为list
        if(roleIds !=null && roleIds.size()>0){
            for (Integer rid : roleIds) {
                List<Menu> menus = roleDao.getMenuByRoleIds(rid);
                //遍历菜单 ,封装到map中
                if(menus != null && menus.size() > 0){
                    for (Menu menu : menus) {
                        //一级菜单
                        Map<String, Object> map = new HashMap<>();
                        map.put("path",menu.getPath());
                        map.put("title",menu.getName());
                        map.put("icon",menu.getIcon());
                        if(menu.getChildren() == null ){
                            map.put("children","[]");
                        }else{
                            //二级菜单结构
                            Set<Map<String, Object>> set2 = new LinkedHashSet<>();
                            //获取二级菜单数据
                            List<Menu> childrens = menu.getChildren();
                            for (Menu children : childrens) {
                                Map<String, Object> map2 = new HashMap<>();
                                map2.put("path",children.getPath());
                                map2.put("title",children.getName());
                                map2.put("linkUrl",children.getLinkUrl());
                                map2.put("children","[]");//目前只有2级
                                //保存2级数据
                                set2.add(map2);
                            }
                            //1级保存2级数据
                            map.put("children",set2);
                        }
                        //保存1级数据
                        set.add(map);
                    }
                }
            }
        }
        return set;
    }


    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void add(User user,Integer[] roleIds) {
        user.setStation("1");
        userDao.add(user);
        int id = user.getId();
        //用户-角色
        setRoleAndUser(id,roleIds);
    }
    //用户-角色
    private void setRoleAndUser(Integer id, Integer[] roleIds) {
        if(roleIds !=null && roleIds.length>0 ){
            for (Integer rid : roleIds) {
                //使用map来封装中间表的数据
                Map<String,Integer> map = new HashMap();
                map.put("rid",rid);
                map.put("id",id);
                userDao.setRoleAndUser(map);
            }
        }
    }

    @Override
    public User findById(int id) {
        return userDao.selectByPrimaryKey(id);
    }
    @Override
    public void update(User user,Integer[] roleId) {
        userDao.updateByPrimaryKeySelective(user);
        Integer id = user.getId();
        //删除原关系表数据
        userDao.deleteRoleAndUser(id);
        //往中间表插入数据
        setRoleAndUser(id,roleId);
    }


    @Override
    public PageResult<User> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<User> page = userDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }


    @Override
    public void deleteById(int id) {
        // 根据角色id 删除角色和用户中间表
        userDao.deleteRoleAndUser(id);
        userDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Integer> findRoleByUserId(int id) {
        List<Integer> list = userDao.findRoleByUserId(id);
        return list;
    }

    @Override
    public User checkUsername(String username) {
        return userDao.checkUsername(username);
    }

    @Override
    public void EditPassword(Integer pid, String encode) {
        userDao.EditPassword(pid,encode);
    }


}
