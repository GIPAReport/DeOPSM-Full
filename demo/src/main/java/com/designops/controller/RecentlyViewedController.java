package com.designops.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation. Autowired;
import org.springframework.web.bind. annotation. CrossOrigin;
import org.springframework.web.bind. annotation. GetMapping;
import org.springframework.web.bind. annotation. PostMapping;
import org.springframework.web.bind. annotation. RequestBody;
import org.springframework.web.bind. annotation. RestController;
import com.designops.exception. ResourceNotFoundException;
import com.designops.model. RecentlyViewed;
import com.designops.repository. RecentlyViewedRepository;
import org.springframework.data.domain. Sort;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RecentlyViewedController {
	

@Autowired
private RecentlyViewedRepository recentlyViewedRepository;

@GetMapping("/recentlyviewed")
public List<RecentlyViewed>getAllRecentlyViewed() throws ResourceNotFoundException {
// return recentlyViewedRepository.findAll(Sort by (Sort.Direction.DESC,
// "lastViewedon"));
return recentlyViewedRepository.findAll();
}


@GetMapping("/mostpopular")
public List<RecentlyViewed> mostPopular() throws ResourceNotFoundException {
return recentlyViewedRepository.findAllMostPopular();
}

@GetMapping("/trendingguides")
public List<RecentlyViewed> trendingGuides() throws ResourceNotFoundException {
List<RecentlyViewed> recentlyViewed= new ArrayList<RecentlyViewed>();
List<RecentlyViewed> foundRecentlyViewed=recentlyViewedRepository.findAllMostPopular();
for(int i=0;i<foundRecentlyViewed.size(); i++)
{
if(foundRecentlyViewed.get(i).getArtifactCategory().equalsIgnoreCase("Guides"))
{
recentlyViewed.add(foundRecentlyViewed.get(i));
}
}
return recentlyViewed;
}


@PostMapping("/recentlyviewed")
public RecentlyViewed insertRecentlyViewed (@RequestBody RecentlyViewed recentlyViewed)
throws ResourceNotFoundException 
{
  RecentlyViewed existingRecentlyViewed = recentlyViewedRepository.findByPageTitle(recentlyViewed.getPageTitle());
  RecentlyViewed existingRecentlyViewedByArtifactId= recentlyViewedRepository.findByArtifactId(recentlyViewed.getArtifactId());
  
  if(existingRecentlyViewedByArtifactId !=null) 
  {
	  int existingViewCount = existingRecentlyViewed.getViewCount();
	  existingRecentlyViewed.setLastViewedOn(recentlyViewed.getLastViewedOn());
	  existingRecentlyViewed.setViewCount(existingViewCount + 1);
	  return recentlyViewedRepository.save (existingRecentlyViewed);
  } else
  {
	  System.out.println("\ns Inside Recebtly Viewd=" + recentlyViewed.getArtifactCategory());
	  return recentlyViewedRepository.save(recentlyViewed);
   }
 }
}
