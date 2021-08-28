package com.designops.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype. Repository;
import com.designops.model. Permission;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
List<Permission> findByPermissionId(int permissionId);
List<Permission> findByPermissionName(String permissionName);
List<Permission> findByPermissionId(Integer permissionId);
}
