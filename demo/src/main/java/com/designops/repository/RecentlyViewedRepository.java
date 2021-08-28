package com.designops.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.designops.model. RecentlyViewed;
public interface RecentlyViewedRepository extends JpaRepository<RecentlyViewed, Integer> {
RecentlyViewed findByPageTitle(String pageTitle);
RecentlyViewed findByArtifactId(int artifactId);

//select *  from from recently_viewed order by  order by last_viewed_on desc limit 15
//@Query(nativeQuery = true, value = "select TOP 15 * from recently viewed order by last_viewed_on desc")

@Query(nativeQuery = true, value = "select *  from  marketplace.recently_viewed order by   last_viewed_on desc limit 15")
public List<RecentlyViewed> findAll();
@Query(nativeQuery = true,
value = "select *  from  marketplace.recently_viewed order by   last_viewed_on desc limit 5")
public List<RecentlyViewed> findAllMostPopular();
}
