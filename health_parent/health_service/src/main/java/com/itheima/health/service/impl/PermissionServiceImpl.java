package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.PermissionException;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;


@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    @Override
    public void deleteById(int id) {
        int count = permissionDao.findCountByPermissionId(id);
        if(count>0){
            throw new PermissionException(MessageConstant.PERMISSION_IN_USE);
        }
        permissionDao.deleteById(id);
    }

    @Override
    public Permission findById(int id) {
        return permissionDao.findById(id);
    }

    @Override
    public void update(Permission permission) {
        permissionDao.update(permission);
    }


    /**
     *@author ${xinpl}
     *@Description 分页
     *@Date 2020/7/6 22:36
     **/
    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());
        Page<Permission> page = permissionDao.selectByCondition(pageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }


}
