package com.designops.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository. Query;
import org.springframework. stereotype. Repository;
import com.designops.model.Capability;
import com.designops.model.Component;
@Repository
public interface ComponentRepository extends MongoRepository<Component, Integer> {
void deleteByArtifactId(int artifactId);
@Query("{ 'status' : ?0,'tenantId': ?1}")
List<Component> findByStatus (String status, Integer tenantId);
List<Component> findByPageTitle(String pageTitle);
List<Component> findByArtifactId(int artifactId);
}
