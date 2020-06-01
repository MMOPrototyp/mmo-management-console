package com.jukusoft.mmo.main.security;

import com.jukusoft.mmo.core.security.UserAccount;
import com.jukusoft.mmo.data.dao.UserDAO;
import com.jukusoft.mmo.data.entity.user.PermissionEntity;
import com.jukusoft.mmo.data.entity.user.RoleEntity;
import com.jukusoft.mmo.data.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public UserAccount findUser(String username) {
        Objects.requireNonNull(username);

        if (username.isEmpty()) {
            throw new IllegalStateException("username does not exists: " + username);
        }

        //find user
        Optional<UserEntity> userOptional = userDAO.findOneByUsername(username);
        UserEntity user = userOptional.orElseThrow(() -> new IllegalStateException("username does not exists, but jwt signature is correct: " + username));

        return new UserAccount(user.getId(), user.getUsername(), getGrantedAuthorities(listPermissionsOfUser(user)));
    }

    private Set<String> listPermissionsOfUser(UserEntity user) {
        Set<String> permissions = new HashSet<>();

        for (RoleEntity role : user.listRoles()) {
            for (PermissionEntity permissionEntity : role.listPermissions()) {
                permissions.add(permissionEntity.getToken());
            }
        }

        return permissions;
    }

    @Cacheable(cacheNames = "granted_authorities")
    private List<GrantedAuthority> getGrantedAuthorities(Set<String> privileges) {
        return privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
