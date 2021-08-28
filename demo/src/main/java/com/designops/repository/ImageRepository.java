package com.designops.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype. Repository;
import com.designops.model. ImageModel;
@Repository
public interface ImageRepository extends MongoRepository<ImageModel, String> {
Optional<ImageModel> findByName(String name);
Optional<ImageModel> findById(Long imageId);
}

