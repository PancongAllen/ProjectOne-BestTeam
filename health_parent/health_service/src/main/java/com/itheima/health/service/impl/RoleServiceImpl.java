package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;



    @Override
    public PageResult findPage(QueryPageBean pageBean) throws Exception {
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        Page page = roleDao.selectByCondition(pageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) throws Exception {
        //新增role 获取id
        roleDao.insertSelective(role);
        Integer id = role.getId();
        //往中间表插入数据
        //角色-菜单
        setRoleAndMenu(id,menuIds);
        //角色-权限
        setRoleAndPermission(id,permissionIds);
    }
    //角色-权限
    private void setRoleAndPermission(Integer id, Integer[] permissionIds) {
        if(permissionIds !=null && permissionIds.length>0 ){
            for (Integer pid : permissionIds) {
                //使用map来封装中间表的数据
                Map<String,Integer> map = new HashMap();
                map.put("pid",pid);
                map.put("id",id);
                roleDao.setRoleAndPermission(map);
            }
        }
    }
    //角色-菜单
    private void setRoleAndMenu(Integer id, Integer[] menuIds) {
        if(menuIds !=null && menuIds.length>0 ){
            for (Integer mid : menuIds) {
                //使用map来封装中间表的数据
                Map<String,Integer> map = new HashMap();
                map.put("mid",mid);
                map.put("id",id);
                roleDao.setRoleAndMenu(map);
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        // 根据角色id 查询角色和用户中间表
        List<Integer> count = roleDao.findUserAndRoleById(id);
        if(count!=null && count.size() > 0){
            throw  new HealthException("被用户引用,不能删除");
        }
        // 根据角色id 删除角色和权限中间表
        roleDao.deleteRoleAndPermission(id);
        // 根据角色id 删除角色和菜单中间表
        roleDao.deleteRoleAndMenu(id);

        // 删除角色
        roleDao.deleteByPrimaryKey(id);
    }

    @Override
    public Role findById(int id) throws Exception {
        return roleDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Role role, Integer[] permissionIds, Integer[] menuIds) throws Exception {
        roleDao.updateByPrimaryKeySelective(role);
        Integer id = role.getId();
        //删除原关系表数据
        roleDao.deleteRoleAndMenu(id);
        roleDao.deleteRoleAndPermission(id);
        //往中间表插入数据
        //角色-菜单
        setRoleAndMenu(id,menuIds);
        //角色-权限
        setRoleAndPermission(id,permissionIds);
    }


    /**
    *@author ${xinpl}
    *@Description 返回权限和菜单的全部数据
    **/
    @Override
    public Map<String, Object> findAll() throws Exception {
        //获取菜单数据
        List<Menu> menus = menuDao.findAll();
        //获取权限数据
        List<Permission> permissions = permissionDao.findAll();
        Map<String,Object> map = new HashMap<>();
        map.put("menus",menus);
        map.put("permissions",permissions);
        return map;
    }

    /**
    *@author ${xinpl}
    *@Description 根据roleid 返回拥有的权限和菜单
    **/
    @Override
    public Map<String, List<Integer>> findPermissionAndMenuByRoleId(int id) {
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> mids = roleDao.findMenuAndRoleById(id);
        List<Integer> pids = roleDao.findPermissionAndRoleById(id);
        map.put("mids",mids);
        map.put("pids",pids);
        return map;
    }
}
