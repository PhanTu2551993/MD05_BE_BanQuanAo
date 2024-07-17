package ra.project_md05.service;


import ra.project_md05.constans.RoleName;
import ra.project_md05.model.entity.Roles;

import java.util.List;

public interface IRoleService {
    List<Roles> getAllRoles();
    Roles findByRoleName(RoleName roleName);
}
