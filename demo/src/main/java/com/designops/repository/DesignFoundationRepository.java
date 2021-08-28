package com.designops.repository;

import java.util.List;
import org.springframework.data.mongodb.repository. MongoRepository;
import org.springframework.data.mongodb.repository. Query;
import org.springframework. stereotype. Repository;
import com.designops.model.Component;
import com.designops.model.DesignFoundation;
@Repository
public interface DesignFoundationRepository extends MongoRepository<DesignFoundation, Integer> {
void deleteByArtifactId(int artifactId);
@Query("{ 'status' : ?, 'tenantId': ?1}")
List<DesignFoundation> findByStatus (String status, Integer tenantId);
List<DesignFoundation> findByPageTitle(String pageTitle);
List<DesignFoundation> findByArtifactId(int artifactId);
}
