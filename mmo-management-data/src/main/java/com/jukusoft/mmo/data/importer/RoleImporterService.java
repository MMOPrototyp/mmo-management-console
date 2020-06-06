package com.jukusoft.mmo.data.importer;

import com.google.common.io.Resources;
import com.jukusoft.mmo.data.dao.PermissionDAO;
import com.jukusoft.mmo.data.dao.RoleDAO;
import com.jukusoft.mmo.data.dao.UserDAO;
import com.jukusoft.mmo.data.entity.user.PermissionEntity;
import com.jukusoft.mmo.data.entity.user.RoleEntity;
import com.jukusoft.mmo.data.entity.user.UserEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class RoleImporterService {

    private static final Logger logger = LoggerFactory.getLogger(RoleImporterService.class);

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PermissionDAO permissionDAO;

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public void importRoles() throws IOException {
        logger.info("create or update default roles");

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("permissions/global_roles.json");
        String content = Resources.toString(url, StandardCharsets.UTF_8);

        JSONArray jsonArray = new JSONArray(content);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);

            String name = json.getString("name");//unique name
            String title = json.getString("title");

            if (roleDAO.existsByName(name)) {
                logger.debug("role '{}' already exists");

                RoleEntity role = roleDAO.findByName(name).orElseThrow();

                role = addDefaultPermissions(json, role);
                roleDAO.save(role);
            } else {
                logger.info("create role '{}'", name);

                //create new global role
                RoleEntity role = new RoleEntity(name, title);
                role = roleDAO.save(role);

                role = addDefaultPermissions(json, role);
                roleDAO.save(role);
            }
        }

        //add admin user to super admin role
        Optional<UserEntity> adminUserOptional = userDAO.findOneByUsername("admin");

        if (adminUserOptional.isPresent()) {
            UserEntity adminUser = adminUserOptional.get();

            //find super admin role
            Optional<RoleEntity> roleEntityOptional = roleDAO.findByName("super_admin");

            if (roleEntityOptional.isPresent()) {
                logger.info("add user {} to role {}", adminUserOptional.get().getUsername(), roleEntityOptional.get().getName());
                adminUser.addRole(roleEntityOptional.get());
                userDAO.save(adminUser);
            }
        }

        logger.info("{} roles found in database", roleDAO.count());
    }

    private RoleEntity addDefaultPermissions(JSONObject json, RoleEntity role) {
        //add default permissions
        JSONArray permissions = json.getJSONArray("default_permissions");

        for (int k = 0; k < permissions.length(); k++) {
            String permission = permissions.getString(k);
            PermissionEntity permissionEntity = permissionDAO.findById(permission).orElseThrow(() -> new IllegalStateException("permission does not exists: " + permission));

            role.addPermission(permissionEntity);
        }

        return role;
    }

}
