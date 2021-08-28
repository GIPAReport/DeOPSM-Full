package com.designops.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository. Query;
import org.springframework.stereotype. Repository;
import com.designops.model.Capability;
import com.designops.model. LibraryForm;
@Repository
public interface LibraryRepository extends MongoRepository<LibraryForm, Integer> {
void deleteByArtifactId(int artifactId);
@Query("{ 'status' : ?0,'tenantId' : ?1}")
List<LibraryForm> findByStatus(String status, Integer tenantId);
List<LibraryForm> findByPageTitle(String pageTitle);
List<LibraryForm> findByArtifactId(int artifactId);
}
