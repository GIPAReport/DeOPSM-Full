package com.designops.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository. Query;
import org.springframework.stereotype. Repository;
import com.designops.model.Permission;
import com.designops.model. RolePermission;
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
List<RolePermission> findByPermissionId(Integer permissionId);
@Query("SELECT rolePermission FROM RolePermission rolePermission WHERE rolePermission.roleId =?1")
List<RolePermission> findByRoleId(int roleId);

}

