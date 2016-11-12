package com.gb.service.interfaces;

import com.gb.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created by gblau on 2016-11-12.
 */
public interface UserService extends BaseService<User> {
    Set<String> findRoles(int id);
    Set<String> findPermissions(int id);
    List<String> findPermissionUrl(String principal);
}
