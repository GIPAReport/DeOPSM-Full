package com.designops.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository. Query;
import org.springframework.stereotype. Repository;
import com.designops.model. Tenant;
import com.designops.model. TenantUser;
@Repository
public interface TenantUserRepository extends JpaRepository<TenantUser, Integer> {
List<TenantUser> findByTenantId(Integer tenantId);
@Query("SELECT tenantUser FROM TenantUser tenantUser WHERE tenantUser.userId =?1")
List<TenantUser> findByUserId(int userId);
@Query("SELECT tenantUser FROM TenantUser tenantUser WHERE tenantUser.roleId=?1")
List<TenantUser> findByRoleId(Integer roleId);


TenantUser findByTenantUserRoleId(Integer tenantUserRoleId);
}
