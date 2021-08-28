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
import com.designops.model.Component;
import com.designops.model.Resource;
import com.designops.model.RichTextBlock;
import com.designops.model.Tenant;
import com.designops.model.Users;
import com.designops.repository.ArtifactRepository;
import com.designops.repository.ComponentRepository;
import com.designops.repository.TenantRepository;
import com.designops.repository.UsersRepository;
import com.designops.utility.Constant;
import com.mongodb.client.result.UpdateResult;

public class ComponentController {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime currentDate = LocalDateTime.now();
	

	@Autowired
	ComponentRepository componentRepository;
	
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
	
	@PostMapping("/components")
	public ResponseEntity<Component> addComponent(@RequestBody Component component)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		
		String modifiedDate= formatter.format(date);
		
		List<Component> existingComponentList= componentRepository.findByPageTitle(component.getPageTitle());
		if(existingComponentList.isEmpty())
		{
			List<String> taskIdList= component.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= new Artifact();
			artifact.setArtifactCategory("Components");
			artifact.setArtifactDescription(component.getPageDescription());
			artifact.setArtifactTitle(component.getPageTitle());
			artifact.setArtifactType(component.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setCreatedOn(currentDate);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(component.getTenantId());
			artifact.setUserId(component.getUserId());
			artifact.setCreatedBy(component.getUserName());
			artifact.setStatus(component.getStatus());
			artifact.setModifiedBy(component.getUserName());
			
			int tenantId= component.getTenantId();
			
			String userName= component.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Components"))
			{
			return new  ResponseEntity<Component>(HttpStatus.NO_CONTENT);
			}
		
			else
			{
			
			artifact.setTenantName(tenant.getTenantName());
			Artifact insertedArtifact= artifactRepository.save(artifact);
			
			component.setArtifactId(insertedArtifact.getArtifactId());
			component.setLastModifiedDate(modifiedDate);
			component.setArtifactCategory("Components");
			component.setTenantName(artifact.getTenantName());
			component.setVersion(1);
			componentRepository.save(component);
			
			return new ResponseEntity<Component>(component,HttpStatus.CREATED);
			}
			
		}
		else
			throw new ArtifactAlreadyExistsException("Artifact already exists::" +component.getPageTitle());
				

	}
	
	@PutMapping(value="/components")
	public ResponseEntity<Component> updateComponent(@RequestBody Component component)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		String modifiedDate= formatter.format(date);
		
		List<Component> existingComponentList= componentRepository.findByArtifactId(component.getArtifactId());
		
		Component existingComponent=null;
		
		if(existingComponentList.isEmpty())
		{
			return new  ResponseEntity<Component>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			existingComponent= existingComponentList.get(0);
			int artifactId=existingComponent.getArtifactId();
			List<String> taskIdList=component.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= artifactRepository.findById(component.getArtifactId()).get();
			artifact.setArtifactDescription(component.getPageDescription());
			artifact.setArtifactTitle(component.getPageTitle());
			artifact.setArtifactType(component.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(component.getTenantId());
			artifact.setStatus(component.getStatus());
			artifact.setModifiedBy(component.getUserName());
			
			int tenantId= component.getTenantId();
			
			String userName= component.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Components"))
			{
			return new  ResponseEntity<Component>(HttpStatus.NO_CONTENT);
			}
			else {
			
			Query select= Query.query(Criteria.where("artifactId").is(artifactId));
			Update update = new Update();
			update.set("version",component.getVersion());
			update.set("status",component.getStatus());
			update.set("artifactId",component.getArtifactId());
			update.set("tenantId",component.getTenantId());
			update.set("userId",component.getUserId());
			update.set("userName",component.getUserName());
			update.set("pageDescription",component.getPageDescription());
			update.set("pageTitle",component.getPageTitle());
			update.set("taskId",component.getTaskId());
			update.set("artifactType",component.getArtifactType());
			update.set("artifactCategory","Components");
			update.set("tenantName",component.getTenantName());
			update.set("lastModifiedDate",modifiedDate);
			update.set("comments",component.getComments());
			update.set("componentDetails",component.getComponentDetails());
			
			UpdateResult updateResult= mongoTemplate.updateMulti(select,update,Component.class);
			return new ResponseEntity<Component>(component,HttpStatus.CREATED);
		}
	}
	}
	
	@GetMapping("/components")
	public ResponseEntity<List<Component>> getAllComponents()
	{
		List<Component> existingComponentList= componentRepository.findAll();
		List<Component> componentList= new ArrayList<Component>();
		Component component= null;
		if(!existingComponentList.isEmpty())
		{
			for(int i=0;i<existingComponentList.size();i++)
			{
			if(!existingComponentList.get(i).getStatus().equalsIgnoreCase("Deleted"))
			{
				component=existingComponentList.get(i);
				componentList.add(component);
			}
			}
			return new ResponseEntity<List<Component>>(componentList,HttpStatus.OK);
		}
		else
			return new ResponseEntity<List<Component>>(HttpStatus.NO_CONTENT);
		
			}
	
	@GetMapping("/components/{artifactId}")
	public ResponseEntity<Component> getComponentById(@PathVariable(value="artifactId") int artifactId)
	{
		List<Component> componentList= componentRepository.findByArtifactId(artifactId);
		Component component= new Component();
		if(componentList.isEmpty())
		{
			return new ResponseEntity<Component>(HttpStatus.NO_CONTENT);
			
		}
		else
			if(componentList.size()==1)
			{
			if(!componentList.get(0).getStatus().equalsIgnoreCase("Deleted"))
			{
				component=componentList.get(0);
			}
			else
				return new ResponseEntity<Component>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<Component>(component,HttpStatus.OK);
		}
		
	
	@DeleteMapping("/components/{artifactId}")
	public ResponseEntity<Void> deleteComponent(@PathVariable(value="artifactId") int artifactId)
	{
		List<Component> componentList= componentRepository.findByArtifactId(artifactId);
		Component component= new Component();
		if(componentList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			for(int i=0;i<componentList.size();i++)
			{
				component=componentList.get(i);
				component.setStatus("Deleted");
				Query select= Query.query(Criteria.where("artifactId").is(component.getArtifactId()));
				Update update= new Update();
				update.set("status",component.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, Component.class);
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

	@GetMapping("/components/{tenantId}/{status}")
	public ResponseEntity<List<Component>> getComponentByStatus(@PathVariable(value="tenantId") int tenantId,@PathVariable(value="status") String status)
	{
		List<Component> componentList= componentRepository.findByStatus(status,tenantId);
		if(componentList.isEmpty())
		{
			return new ResponseEntity<List<Component>>(componentList,HttpStatus.NO_CONTENT);
			
		}
		else
			return new ResponseEntity<List<Component>>(componentList,HttpStatus.OK);
		}
	
	@PutMapping("/approval/components/{artifactId}/{status}")
	public ResponseEntity<Void> updateComponentStatus(@PathVariable(value="artifactId") int artifactId,@PathVariable(value="status") String status)
	{
		Artifact artifact=artifactRepository.findById(artifactId).get();
		artifact.setStatus(status);
		List<Component> componentList= componentRepository.findByArtifactId(artifactId);
		Component component= new Component();
		if(componentList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			
				component=componentList.get(0);
				component.setStatus(status);
				Query select= Query.query(Criteria.where("artifactId").is(component.getArtifactId()));
				Update update= new Update();
				update.set("status",component.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, Component.class);
				artifactRepository.save(artifact);
			
	}
	
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
	

			
}