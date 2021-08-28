package com.designops.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype. Repository;
import com.designops.model. Role;
import com.designops.model.Users;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
Role findByRoleName(String roleName);
List<Role> findById(int roleId);
Role findByRoleId(int roleId);
}
