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
import com.designops.model.DesignFoundation;
import com.designops.model.Resource;
import com.designops.model.RichTextBlock;
import com.designops.model.Tenant;
import com.designops.model.Users;
import com.designops.repository.ArtifactRepository;
import com.designops.repository.DesignFoundationRepository;
import com.designops.repository.TenantRepository;
import com.designops.repository.UsersRepository;
import com.designops.utility.Constant;
import com.mongodb.client.result.UpdateResult;

public class DesignFoundationController {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime currentDate = LocalDateTime.now();
	

	@Autowired
	DesignFoundationRepository designFoundationRepository;
	
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
	
	@PostMapping("/foundations")
	public ResponseEntity<DesignFoundation> addDesignFoundation(@RequestBody DesignFoundation designFoundation)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		
		String modifiedDate= formatter.format(date);
		
		List<DesignFoundation> existingDesignFoundationList= designFoundationRepository.findByPageTitle(designFoundation.getPageTitle());
		if(existingDesignFoundationList.isEmpty())
		{
			List<String> taskIdList= designFoundation.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= new Artifact();
			artifact.setArtifactCategory("Design Foundations");
			artifact.setArtifactDescription(designFoundation.getPageDescription());
			artifact.setArtifactTitle(designFoundation.getPageTitle());
			artifact.setArtifactType(designFoundation.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setCreatedOn(currentDate);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(designFoundation.getTenantId());
			artifact.setUserId(designFoundation.getUserId());
			artifact.setCreatedBy(designFoundation.getUserName());
			artifact.setStatus(designFoundation.getStatus());
			artifact.setModifiedBy(designFoundation.getUserName());
			
			int tenantId= designFoundation.getTenantId();
			
			String userName= designFoundation.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("DesignFoundations"))
			{
			return new  ResponseEntity<DesignFoundation>(HttpStatus.NO_CONTENT);
			}
		
			else
			{
			
			artifact.setTenantName(tenant.getTenantName());
			Artifact insertedArtifact= artifactRepository.save(artifact);
			
			designFoundation.setArtifactId(insertedArtifact.getArtifactId());
			designFoundation.setLastModifiedDate(modifiedDate);
			designFoundation.setArtifactCategory("Design Foundations");
			designFoundation.setTenantName(artifact.getTenantName());
			designFoundation.setVersion(1);
			designFoundationRepository.save(designFoundation);
			
			return new ResponseEntity<DesignFoundation>(designFoundation,HttpStatus.CREATED);
			}
			
		}
		else
			throw new ArtifactAlreadyExistsException("Artifact already exists::" +designFoundation.getPageTitle());
				

	}
	
	@PutMapping(value="/foundations")
	public ResponseEntity<DesignFoundation> updateDesignFoundation(@RequestBody DesignFoundation designFoundation)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		String modifiedDate= formatter.format(date);
		
		List<DesignFoundation> existingDesignFoundationList= designFoundationRepository.findByArtifactId(designFoundation.getArtifactId());
		
		DesignFoundation existingDesignFoundation=null;
		
		if(existingDesignFoundationList.isEmpty())
		{
			return new  ResponseEntity<DesignFoundation>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			existingDesignFoundation= existingDesignFoundationList.get(0);
			int artifactId=existingDesignFoundation.getArtifactId();
			List<String> taskIdList=designFoundation.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= artifactRepository.findById(designFoundation.getArtifactId()).get();
			artifact.setArtifactDescription(designFoundation.getPageDescription());
			artifact.setArtifactTitle(designFoundation.getPageTitle());
			artifact.setArtifactType(designFoundation.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(designFoundation.getTenantId());
			artifact.setStatus(designFoundation.getStatus());
			artifact.setModifiedBy(designFoundation.getUserName());
			
			int tenantId= designFoundation.getTenantId();
			
			String userName= designFoundation.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Design Foundations"))
			{
			return new  ResponseEntity<DesignFoundation>(HttpStatus.NO_CONTENT);
			}
			else {
			
			Query select= Query.query(Criteria.where("artifactId").is(artifactId));
			Update update = new Update();
			update.set("version",designFoundation.getVersion());
			update.set("status",designFoundation.getStatus());
			update.set("artifactId",designFoundation.getArtifactId());
			update.set("tenantId",designFoundation.getTenantId());
			update.set("userId",designFoundation.getUserId());
			update.set("userName",designFoundation.getUserName());
			update.set("pageDescription",designFoundation.getPageDescription());
			update.set("pageTitle",designFoundation.getPageTitle());
			update.set("taskId",designFoundation.getTaskId());
			update.set("artifactType",designFoundation.getArtifactType());
			update.set("artifactCategory","DesignFoundations");
			update.set("tenantName",designFoundation.getTenantName());
			update.set("lastModifiedDate",modifiedDate);
			update.set("richTextArray",designFoundation.getRichTextArray());
			update.set("image",designFoundation.getImage());
			update.set("colorSection",designFoundation.getColorSection());
			UpdateResult updateResult= mongoTemplate.updateMulti(select,update,DesignFoundation.class);
			return new ResponseEntity<DesignFoundation>(designFoundation,HttpStatus.CREATED);
		}
	}
	}
	
	@GetMapping("/foundations")
	public ResponseEntity<List<DesignFoundation>> getAllDesignFoundations()
	{
		List<DesignFoundation> existingDesignFoundationList= designFoundationRepository.findAll();
		List<DesignFoundation> designFoundationList= new ArrayList<DesignFoundation>();
		DesignFoundation designFoundation= null;
		if(!existingDesignFoundationList.isEmpty())
		{
			for(int i=0;i<existingDesignFoundationList.size();i++)
			{
			if(!existingDesignFoundationList.get(i).getStatus().equalsIgnoreCase("Deleted"))
			{
				designFoundation=existingDesignFoundationList.get(i);
				designFoundationList.add(designFoundation);
			}
			}
			return new ResponseEntity<List<DesignFoundation>>(designFoundationList,HttpStatus.OK);
		}
		else
			return new ResponseEntity<List<DesignFoundation>>(HttpStatus.NO_CONTENT);
		
			}
	
	@GetMapping("/foundations/{artifactId}")
	public ResponseEntity<DesignFoundation> getDesignFoundationById(@PathVariable(value="artifactId") int artifactId)
	{
		List<DesignFoundation> designFoundationList= designFoundationRepository.findByArtifactId(artifactId);
		DesignFoundation designFoundation= new DesignFoundation();
		if(designFoundationList.isEmpty())
		{
			return new ResponseEntity<DesignFoundation>(HttpStatus.NO_CONTENT);
			
		}
		else
			if(designFoundationList.size()==1)
			{
			if(!designFoundationList.get(0).getStatus().equalsIgnoreCase("Deleted"))
			{
				designFoundation=designFoundationList.get(0);
			}
			else
				return new ResponseEntity<DesignFoundation>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<DesignFoundation>(designFoundation,HttpStatus.OK);
		}
		
	
	@DeleteMapping("/foundations/{artifactId}")
	public ResponseEntity<Void> deleteDesignFoundation(@PathVariable(value="artifactId") int artifactId)
	{
		List<DesignFoundation> designFoundationList= designFoundationRepository.findByArtifactId(artifactId);
		DesignFoundation designFoundation= new DesignFoundation();
		if(designFoundationList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			for(int i=0;i<designFoundationList.size();i++)
			{
				designFoundation=designFoundationList.get(i);
				designFoundation.setStatus("Deleted");
				Query select= Query.query(Criteria.where("artifactId").is(designFoundation.getArtifactId()));
				Update update= new Update();
				update.set("status",designFoundation.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, DesignFoundation.class);
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

	@GetMapping("/foundations/{tenantId}/{status}")
	public ResponseEntity<List<DesignFoundation>> getDesignFoundationByStatus(@PathVariable(value="tenantId") int tenantId,@PathVariable(value="status") String status)
	{
		List<DesignFoundation> designFoundationList= designFoundationRepository.findByStatus(status,tenantId);
		if(designFoundationList.isEmpty())
		{
			return new ResponseEntity<List<DesignFoundation>>(designFoundationList,HttpStatus.NO_CONTENT);
			
		}
		else
			return new ResponseEntity<List<DesignFoundation>>(designFoundationList,HttpStatus.OK);
		}
	
	@PutMapping("/approval/foundations/{artifactId}/{status}")
	public ResponseEntity<Void> updateDesignFoundationStatus(@PathVariable(value="artifactId") int artifactId,@PathVariable(value="status") String status)
	{
		Artifact artifact=artifactRepository.findById(artifactId).get();
		artifact.setStatus(status);
		List<DesignFoundation> designFoundationList= designFoundationRepository.findByArtifactId(artifactId);
		DesignFoundation designFoundation= new DesignFoundation();
		if(designFoundationList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			
				designFoundation=designFoundationList.get(0);
				designFoundation.setStatus(status);
				Query select= Query.query(Criteria.where("artifactId").is(designFoundation.getArtifactId()));
				Update update= new Update();
				update.set("status",designFoundation.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, DesignFoundation.class);
				artifactRepository.save(artifact);
			
	}
	
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
	

			
}
