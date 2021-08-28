//package com.designops.repository;
//
//import java.util.List;
//import org.springframework.data.elasticsearch.annotations.Query;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework. stereotype. Repository;
//import com.designops.model.CMS;
//@Repository
//public interface ArtifactElasticSearchRepository extends ElasticsearchRepository<CMS, Integer> {
//List<CMS> findByPageDescriptionOrUserNameOrArtifactTypeOrPageTitleOrTaskidorArtifactCategoryContaining (String desc, String userName, String type, String title,String taskId, String artifactcategory);
//
//}