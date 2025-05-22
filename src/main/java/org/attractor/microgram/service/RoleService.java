package org.attractor.microgram.service;

import org.attractor.microgram.model.Role;

public interface RoleService {

    Role findByRoleName(String roleName);
}
