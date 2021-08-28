package com.designops.repository;

import java.util.List;
import org.springframework.data.mongodb.repository. MongoRepository;
import org.springframework.data.mongodb.repository. Query;
import org.springframework.stereotype. Repository;
import com.designops.model. Guide;
@Repository
public interface GuideRepository extends MongoRepository<Guide, Integer> {
void deleteByArtifactId(int artifactId);
@Query("{ 'status': ?0, 'tenantId': ?1}")
List<Guide> findByStatus (String status, Integer tenantId);
List<Guide> findByPageTitle(String pageTitle);
List<Guide> findByArtifactId(int artifactId);
}
