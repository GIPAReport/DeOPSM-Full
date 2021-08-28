package com.designops.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository. Query;
import org.springframework.stereotype. Repository;
import com.designops.model. Tenant;
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
// Tenant findBytenanti(int projectid);
@Query("SELECT u FROM Tenant u WHERE u. tenantName = ?1 and u.isActive ='1'")
Tenant findByTenantNameIgnoreCase(String tenantName);
List<Tenant> findByTenantName(String tenantName);

List<Tenant> findByTenantId(Integer tenantId);
}
