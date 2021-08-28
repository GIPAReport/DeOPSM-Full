package com.designops.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.suggest.SortBy;
import org.springframework.beans.factory.annotation. Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind. annotation. CrossOrigin;
import org.springframework.web.bind. annotation. GetMapping;
import org.springframework.web.bind. annotation. RestController;
import com.designops.model.Artifact;
import com.designops.model.Capability;
import com.designops.model.Component;
import com.designops.model.LatestUpdates;
import com.designops.model. Updates;
import com.designops.repository. ArtifactRepository;
import com.designops.repository.CapabilityRepository;
import com.designops.repository.ComponentRepository;
import com.designops.repository.DesignFoundationRepository;
import com.designops.repository.GuideRepository;
import com.designops.repository.LibraryRepository;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class LatestUpdatesController {
@Autowired
ArtifactRepository artifactRepository;
@Autowired
CapabilityRepository capabilityRepository;
@Autowired
ComponentRepository componentRepository;
@Autowired
DesignFoundationRepository designFoundationRepository;
@Autowired
GuideRepository guideRepository;
@Autowired
LibraryRepository libraryRepository;
	
	
@GetMapping("/componentlatestupdates")
public List<LatestUpdates> getComponentLatestUpdates() {
List<LatestUpdates> latestUpdatesComponentList = new ArrayList<LatestUpdates>();
List<LatestUpdates> latestUpdatesList = getLatestUpdates();
for (int i = 0; i < latestUpdatesList.size(); i++)
{
LatestUpdates latestUpdates= latestUpdatesList.get(i);
if (latestUpdates.getArtifactCategory().equalsIgnoreCase("Components")) {
latestUpdatesComponentList.add(latestUpdates);
if(latestUpdatesComponentList.size() >=30)
{
return latestUpdatesComponentList;
}
}
}
return latestUpdatesComponentList;
}


@GetMapping("/capabilitieslatestupdates")
public List<LatestUpdates> getCapabilitiesLatestUpdates() 
{
 List<LatestUpdates> latestUpdatesCapabilitiesList = new ArrayList<LatestUpdates>();
 List<LatestUpdates> latestUpdatesList = getLatestUpdates();
 for (int i = 0; i < latestUpdatesList.size(); i++) 
 {
  LatestUpdates latestUpdates = latestUpdatesList.get(i);
  if (latestUpdates.getArtifactCategory().equalsIgnoreCase("Capabilities")) 
  {
   latestUpdatesCapabilitiesList.add(latestUpdates);
   if(latestUpdatesCapabilitiesList.size() >=30)
   {
	   return latestUpdatesCapabilitiesList;
    }
   }
  }
  return latestUpdatesCapabilitiesList;
 }




@GetMapping("/designerguideslatestupdates")
public List<LatestUpdates> getDesignerGuidesLatestUpdates() 
{
 List<LatestUpdates> latestUpdatesDesignerGuidesList = new ArrayList<LatestUpdates>();
 List<LatestUpdates> latestUpdatesList = getLatestUpdates();
 for (int i = 0; i < latestUpdatesList.size(); i++) 
 {
   LatestUpdates latestUpdates = latestUpdatesList.get(i);
   if (latestUpdates.getArtifactCategory().equalsIgnoreCase("Guides")&&latestUpdates.getArtifactType().equalsIgnoreCase("Design")) {
   latestUpdatesDesignerGuidesList.add(latestUpdates);
   if(latestUpdatesDesignerGuidesList.size() >=30)
   {
	   return latestUpdatesDesignerGuidesList;
   }
  }
 }
 return latestUpdatesDesignerGuidesList;
 
}



@GetMapping("/developerguideslatestupdates")
public List<LatestUpdates> getDeveloperGuidesLatestUpdates()
{
List<LatestUpdates> latestUpdatesDeveloperGuidesList = new ArrayList<LatestUpdates>();
List<LatestUpdates> latestUpdatesList = getLatestUpdates();
for (int i = 0; i < latestUpdatesList.size(); i++) {
LatestUpdates latestUpdates = latestUpdatesList.get(i);
if (latestUpdates.getArtifactCategory().equalsIgnoreCase("Guides")&&latestUpdates.getArtifactType().equalsIgnoreCase("Development"))
latestUpdatesDeveloperGuidesList.add(latestUpdates);
  if(latestUpdatesDeveloperGuidesList.size() >=30)
  {
    return latestUpdatesDeveloperGuidesList;
   }
  }
return latestUpdatesDeveloperGuidesList;
}


@GetMapping("/latestupdates")
public List<LatestUpdates> getLatestUpdates() 
{
 List<LatestUpdates> latestUpdatesList = new ArrayList<LatestUpdates>();
 List<Artifact> artifactList = artifactRepository.findAll(Sort.by(Sort.Direction.DESC, "LastModifiedOn"));
 for (int i = 0; i < artifactList.size(); i++) {
 LatestUpdates latestUpdates = new LatestUpdates();
 Artifact foundArtifact = artifactList.get(i);
 if (foundArtifact.isActive() == true && foundArtifact.getStatus().equalsIgnoreCase("Approved")) 
 {
  latestUpdates.setArtifactCategory(artifactList.get(i).getArtifactCategory());
  latestUpdates.setArtifactId(artifactList.get(i).getArtifactId());
  latestUpdates.setPageTitle(artifactList.get(i).getArtifactTitle());
  latestUpdates.setTenantId(artifactList.get(i).getTenantId());
  latestUpdates.setTenantName (artifactList.get(i).getTenantName());
  latestUpdates.setArtifactType(artifactList.get(i).getArtifactType());
//  latestUpdates.set
//  latestUpdates.setUpdatesDTOgetUpdatesDTO(artifactList.get(i).getArtifactId(), artifactList.get(i).getArtifactCategory());
  latestUpdatesList.add(latestUpdates);
}
}
return latestUpdatesList;
}



}
