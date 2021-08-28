package com.designops.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository. Query;
import org.springframework.data.repository.history. RevisionRepository;
import org.springframework. stereotype. Repository;
import com.designops.model. Artifact;
@Repository
public interface ArtifactRepository extends RevisionRepository<Artifact, Integer, Integer>, JpaRepository<Artifact, Integer>
{
Artifact findByArtifactTitle(String artifactTitle);

@Query("select artifact from Artifact artifact where artifact. tenantId = ?1")
List<Artifact> findAllByTenantOrderByLastModifiedOnAsc(Integer tenantId);

@Query("select artifact from Artifact artifact where artifact.createdBy = ?1")
List<Artifact> findAllByUserName(String userName);
@Query("select artifact from Artifact artifact where artifact.userId = ?1")
List<Artifact> findbyUserId(Integer userId);
}
