package com.designops.controller;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javers.spring.annotation.JaversAuditable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.designops.exception.ArtifactAlreadyExistsException;
import com.designops.model.Artifact;
import com.designops.model.CodeSection;
import com.designops.model.Guide;
import com.designops.model.Resource;
import com.designops.model.RichTextBlock;
import com.designops.model.Tenant;
import com.designops.model.Users;
import com.designops.repository.ArtifactRepository;
import com.designops.repository.GuideRepository;
import com.designops.repository.TenantRepository;
import com.designops.repository.UsersRepository;
import com.designops.utility.Constant;
import com.mongodb.client.result.UpdateResult;

public class GuideController {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime currentDate = LocalDateTime.now();
	

	@Autowired
	GuideRepository guideRepository;
	
	@Autowired
	ArtifactRepository artifactRepository;
	
	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	TenantRepository tenantRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	Date date= new Date();
	
	SimpleDateFormat formatter= new  SimpleDateFormat("dd MMM yyyy");
	
	@PostMapping("/guides")
	public ResponseEntity<Guide> addGuide(@RequestBody Guide guide)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		
		String modifiedDate= formatter.format(date);
		
		List<Guide> existingGuideList= guideRepository.findByPageTitle(guide.getPageTitle());
		if(existingGuideList.isEmpty())
		{
			List<String> taskIdList= guide.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= new Artifact();
			artifact.setArtifactCategory("Guides");
			artifact.setArtifactDescription(guide.getPageDescription());
			artifact.setArtifactTitle(guide.getPageTitle());
			artifact.setArtifactType(guide.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setCreatedOn(currentDate);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(guide.getTenantId());
			artifact.setUserId(guide.getUserId());
			artifact.setCreatedBy(guide.getUserName());
			artifact.setStatus(guide.getStatus());
			artifact.setModifiedBy(guide.getUserName());
			
			int tenantId= guide.getTenantId();
			
			String userName= guide.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Guides"))
			{
			return new  ResponseEntity<Guide>(HttpStatus.NO_CONTENT);
			}
		
			else
			{
			
			artifact.setTenantName(tenant.getTenantName());
			Artifact insertedArtifact= artifactRepository.save(artifact);
			
			guide.setArtifactId(insertedArtifact.getArtifactId());
			guide.setLastModifiedDate(modifiedDate);
			guide.setArtifactCategory("Guides");
			guide.setTenantName(artifact.getTenantName());
			guide.setVersion(1);
			guideRepository.save(guide);
			
			return new ResponseEntity<Guide>(guide,HttpStatus.CREATED);
			}
			
		}
		else
			throw new ArtifactAlreadyExistsException("Artifact already exists::" +guide.getPageTitle());
				

	}
	
	@PutMapping(value="/guides")
	public ResponseEntity<Guide> updateGuide(@RequestBody Guide guide)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		String modifiedDate= formatter.format(date);
		
		List<Guide> existingGuideList= guideRepository.findByArtifactId(guide.getArtifactId());
		
		Guide existingGuide=null;
		
		if(existingGuideList.isEmpty())
		{
			return new  ResponseEntity<Guide>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			existingGuide= existingGuideList.get(0);
			int artifactId=existingGuide.getArtifactId();
			List<String> taskIdList=guide.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= artifactRepository.findById(guide.getArtifactId()).get();
			artifact.setArtifactDescription(guide.getPageDescription());
			artifact.setArtifactTitle(guide.getPageTitle());
			artifact.setArtifactType(guide.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(guide.getTenantId());
			artifact.setStatus(guide.getStatus());
			artifact.setModifiedBy(guide.getUserName());
			
			int tenantId= guide.getTenantId();
			
			String userName= guide.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Guides"))
			{
			return new  ResponseEntity<Guide>(HttpStatus.NO_CONTENT);
			}
			else {
			
			Query select= Query.query(Criteria.where("artifactId").is(artifactId));
			Update update = new Update();
			update.set("version",guide.getVersion());
			update.set("status",guide.getStatus());
			update.set("artifactId",guide.getArtifactId());
			update.set("tenantId",guide.getTenantId());
			update.set("userId",guide.getUserId());
			update.set("userName",guide.getUserName());
			update.set("pageDescription",guide.getPageDescription());
			update.set("pageTitle",guide.getPageTitle());
			update.set("taskId",guide.getTaskId());
			update.set("artifactType",guide.getArtifactType());
			update.set("artifactCategory","Guides");
			update.set("tenantName",guide.getTenantName());
			update.set("lastModifiedDate",modifiedDate);
			update.set("richTextArray",guide.getRichTextArray());
			update.set("image",guide.getImage());
			UpdateResult updateResult= mongoTemplate.updateMulti(select,update,Guide.class);
			return new ResponseEntity<Guide>(guide,HttpStatus.CREATED);
		}
	}
	}
	
	@GetMapping("/guides")
	public ResponseEntity<List<Guide>> getAllGuides()
	{
		List<Guide> existingGuideList= guideRepository.findAll();
		List<Guide> guideList= new ArrayList<Guide>();
		Guide guide= null;
		if(!existingGuideList.isEmpty())
		{
			for(int i=0;i<existingGuideList.size();i++)
			{
			if(!existingGuideList.get(i).getStatus().equalsIgnoreCase("Deleted"))
			{
				guide=existingGuideList.get(i);
				guideList.add(guide);
			}
			}
			return new ResponseEntity<List<Guide>>(guideList,HttpStatus.OK);
		}
		else
			return new ResponseEntity<List<Guide>>(HttpStatus.NO_CONTENT);
		
			}
	
	@GetMapping("/guides/{artifactId}")
	public ResponseEntity<Guide> getGuideById(@PathVariable(value="artifactId") int artifactId)
	{
		List<Guide> guideList= guideRepository.findByArtifactId(artifactId);
		Guide guide= new Guide();
		if(guideList.isEmpty())
		{
			return new ResponseEntity<Guide>(HttpStatus.NO_CONTENT);
			
		}
		else
			if(guideList.size()==1)
			{
			if(!guideList.get(0).getStatus().equalsIgnoreCase("Deleted"))
			{
				guide=guideList.get(0);
			}
			else
				return new ResponseEntity<Guide>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<Guide>(guide,HttpStatus.OK);
		}
		
	
	@DeleteMapping("/guides/{artifactId}")
	public ResponseEntity<Void> deleteGuide(@PathVariable(value="artifactId") int artifactId)
	{
		List<Guide> guideList= guideRepository.findByArtifactId(artifactId);
		Guide guide= new Guide();
		if(guideList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			for(int i=0;i<guideList.size();i++)
			{
				guide=guideList.get(i);
				guide.setStatus("Deleted");
				Query select= Query.query(Criteria.where("artifactId").is(guide.getArtifactId()));
				Update update= new Update();
				update.set("status",guide.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, Guide.class);
			}
	}
	if(artifactRepository.findById(artifactId)==null)
	{
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	else
	{
		Artifact artifact= artifactRepository.findById(artifactId).get();
		artifact.setActive(false);
		artifact.setStatus("Deleted");
		artifactRepository.save(artifact);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
	
}

	@GetMapping("/guides/{tenantId}/{status}")
	public ResponseEntity<List<Guide>> getGuideByStatus(@PathVariable(value="tenantId") int tenantId,@PathVariable(value="status") String status)
	{
		List<Guide> guideList= guideRepository.findByStatus(status,tenantId);
		if(guideList.isEmpty())
		{
			return new ResponseEntity<List<Guide>>(guideList,HttpStatus.NO_CONTENT);
			
		}
		else
			return new ResponseEntity<List<Guide>>(guideList,HttpStatus.OK);
		}
	
	@PutMapping("/approval/guides/{artifactId}/{status}")
	public ResponseEntity<Void> updateGuideStatus(@PathVariable(value="artifactId") int artifactId,@PathVariable(value="status") String status)
	{
		Artifact artifact=artifactRepository.findById(artifactId).get();
		artifact.setStatus(status);
		List<Guide> guideList= guideRepository.findByArtifactId(artifactId);
		Guide guide= new Guide();
		if(guideList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			
				guide=guideList.get(0);
				guide.setStatus(status);
				Query select= Query.query(Criteria.where("artifactId").is(guide.getArtifactId()));
				Update update= new Update();
				update.set("status",guide.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, Guide.class);
				artifactRepository.save(artifact);
			
	}
	
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
	

			
}
