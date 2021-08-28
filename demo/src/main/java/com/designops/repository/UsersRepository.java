package com.designops.repository;

import java.util.List;
import java.util. Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository. Query;
import org.springframework.data.repository.history. RevisionRepository;
import org.springframework.stereotype. Repository;
import com.designops.model. Artifact;
import com.designops.model. Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
// Users findByemail(String email);
// Users findByuserName(String username);
//
// Users findById(int id);
// List<Users> findByUser Name (String userName);
@Query("SELECT u FROM Users u WHERE u.email = ?1 and u.isActive = '1'")
Users findByEmailIgnoreCase(String email);
List<Users> findByUserId(Integer userId);
List<Users> findByNameIgnoreCase(String userName);
}
