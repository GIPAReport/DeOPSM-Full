package com.designops.repository;

import java.util.List;
import org.springframework.data.mongodb.repository. MongoRepository;
import org.springframework.data.mongodb.repository. Query;
import org.springframework. stereotype. Repository;
import com.designops.model.Capability;
@Repository
public interface CapabilityRepository extends MongoRepository<Capability, Integer> {
void deleteByArtifactId(int artifactId);
@Query("{ 'status' : ?0 , 'tenantId' : ?1}")
List<Capability> findByStatus (String status, Integer tenantId);
List<Capability> findByPageTitle(String pageTitle);
List<Capability> findByArtifactId(int artifactId);
}
