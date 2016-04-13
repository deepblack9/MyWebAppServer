package com.bruce.platform.dao;

import java.util.List;

import com.bruce.platform.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    /**获取所有用户信息
     * @return List<User>
     */
    List<SysUser> getAll();
}