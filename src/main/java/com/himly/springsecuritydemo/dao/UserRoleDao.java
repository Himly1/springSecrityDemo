package com.himly.springsecuritydemo.dao;

import com.himly.springsecuritydemo.model.UserRole;

import java.util.List;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
public interface UserRoleDao {

    public List<UserRole> getUserRolesByIds(List<Long> ids);

    public List<UserRole> getUserRolesByTargetIdAndeTargetTyp(UserRole userRole);
}
