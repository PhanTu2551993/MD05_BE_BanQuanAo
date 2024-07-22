package ra.project_md05.service;


import ra.project_md05.constants.RoleName;
import ra.project_md05.model.entity.Roles;

import java.util.List;

public interface IRoleService {
    List<Roles> getAllRoles();
    Roles findByRoleName(RoleName roleName);
}
