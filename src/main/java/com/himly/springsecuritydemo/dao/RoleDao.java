package com.himly.springsecuritydemo.dao;





import com.himly.springsecuritydemo.model.Role;
import org.apache.ibatis.annotations.Select;

import javax.annotation.Resource;
import java.util.List;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
public interface RoleDao {

    public List<Role> getRolesByIds(List<Long> ids);
}
