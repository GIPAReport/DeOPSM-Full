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
import com.designops.model.Capability;
import com.designops.model.Resource;
import com.designops.model.RichTextBlock;
import com.designops.model.Tenant;
import com.designops.model.Users;
import com.designops.repository.ArtifactRepository;
import com.designops.repository.CapabilityRepository;
import com.designops.repository.TenantRepository;
import com.designops.repository.UsersRepository;
import com.designops.utility.Constant;
import com.mongodb.client.result.UpdateResult;

public class CapabilityController {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime currentDate = LocalDateTime.now();
	

	@Autowired
	CapabilityRepository capabilityRepository;
	
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
	
	@PostMapping("/capabilities")
	public ResponseEntity<Capability> addCapability(@RequestBody Capability capability)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		
		String modifiedDate= formatter.format(date);
		
		List<Capability> existingCapabilityList= capabilityRepository.findByPageTitle(capability.getPageTitle());
		if(existingCapabilityList.isEmpty())
		{
			List<String> taskIdList= capability.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= new Artifact();
			artifact.setArtifactCategory("Capabilities");
			artifact.setArtifactDescription(capability.getPageDescription());
			artifact.setArtifactTitle(capability.getPageTitle());
			artifact.setArtifactType(capability.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setCreatedOn(currentDate);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(capability.getTenantId());
			artifact.setUserId(capability.getUserId());
			artifact.setCreatedBy(capability.getUserName());
			artifact.setStatus(capability.getStatus());
			artifact.setModifiedBy(capability.getUserName());
			
			int tenantId= capability.getTenantId();
			
			String userName= capability.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Capabilitys"))
			{
			return new  ResponseEntity<Capability>(HttpStatus.NO_CONTENT);
			}
		
			else
			{
			
			artifact.setTenantName(tenant.getTenantName());
			Artifact insertedArtifact= artifactRepository.save(artifact);
			
			capability.setArtifactId(insertedArtifact.getArtifactId());
			capability.setLastModifiedDate(modifiedDate);
			capability.setArtifactCategory("Capabilities");
			capability.setTenantName(artifact.getTenantName());
			capability.setVersion(1);
			capabilityRepository.save(capability);
			
			return new ResponseEntity<Capability>(capability,HttpStatus.CREATED);
			}
			
		}
		else
			throw new ArtifactAlreadyExistsException("Artifact already exists::" +capability.getPageTitle());
				

	}
	
	@PutMapping(value="/capabilities")
	public ResponseEntity<Capability> updateCapability(@RequestBody Capability capability)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		String modifiedDate= formatter.format(date);
		
		List<Capability> existingCapabilityList= capabilityRepository.findByArtifactId(capability.getArtifactId());
		
		Capability existingCapability=null;
		
		if(existingCapabilityList.isEmpty())
		{
			return new  ResponseEntity<Capability>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			existingCapability= existingCapabilityList.get(0);
			int artifactId=existingCapability.getArtifactId();
			List<String> taskIdList=capability.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= artifactRepository.findById(capability.getArtifactId()).get();
			artifact.setArtifactDescription(capability.getPageDescription());
			artifact.setArtifactTitle(capability.getPageTitle());
			artifact.setArtifactType(capability.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(capability.getTenantId());
			artifact.setStatus(capability.getStatus());
			artifact.setModifiedBy(capability.getUserName());
			
			int tenantId= capability.getTenantId();
			
			String userName= capability.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Capabilities"))
			{
			return new  ResponseEntity<Capability>(HttpStatus.NO_CONTENT);
			}
			
			else
			
				
			{
			
			Query select= Query.query(Criteria.where("artifactId").is(artifactId));
			Update update = new Update();
			update.set("version",capability.getVersion());
			update.set("status",capability.getStatus());
			update.set("artifactId",capability.getArtifactId());
			update.set("tenantId",capability.getTenantId());
			update.set("userId",capability.getUserId());
			update.set("userName",capability.getUserName());
			update.set("pageDescription",capability.getPageDescription());
			update.set("pageTitle",capability.getPageTitle());
			update.set("taskId",capability.getTaskId());
			update.set("artifactType",capability.getArtifactType());
			update.set("artifactCategory","Capabilitys");
			update.set("tenantName",capability.getTenantName());
			update.set("lastModifiedDate",modifiedDate);
			update.set("comments",capability.getComments());
			update.set("capabilityDetails",capability.getCapabilityDetails());
			
			UpdateResult updateResult= mongoTemplate.updateMulti(select,update,Capability.class);
			return new ResponseEntity<Capability>(capability,HttpStatus.CREATED);
		}
	}
	}
	
	@GetMapping("/capabilities")
	public ResponseEntity<List<Capability>> getAllCapabilitys()
	{
		List<Capability> existingCapabilityList= capabilityRepository.findAll();
		List<Capability> capabilityList= new ArrayList<Capability>();
		Capability capability= null;
		if(!existingCapabilityList.isEmpty())
		{
			for(int i=0;i<existingCapabilityList.size();i++)
			{
			if(!existingCapabilityList.get(i).getStatus().equalsIgnoreCase("Deleted"))
			{
				capability=existingCapabilityList.get(i);
				capabilityList.add(capability);
			}
			}
			return new ResponseEntity<List<Capability>>(capabilityList,HttpStatus.OK);
		}
		else
			return new ResponseEntity<List<Capability>>(HttpStatus.NO_CONTENT);
		
			}
	
	@GetMapping("/capabilities/{artifactId}")
	public ResponseEntity<Capability> getCapabilityById(@PathVariable(value="artifactId") int artifactId)
	{
		List<Capability> capabilityList= capabilityRepository.findByArtifactId(artifactId);
		Capability capability= new Capability();
		if(capabilityList.isEmpty())
		{
			return new ResponseEntity<Capability>(HttpStatus.NO_CONTENT);
			
		}
		else
			if(capabilityList.size()==1)
			{
			if(!capabilityList.get(0).getStatus().equalsIgnoreCase("Deleted"))
			{
				capability=capabilityList.get(0);
			}
			else
				return new ResponseEntity<Capability>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<Capability>(capability,HttpStatus.OK);
		}
		
	
	@DeleteMapping("/capabilities/{artifactId}")
	public ResponseEntity<Void> deleteCapability(@PathVariable(value="artifactId") int artifactId)
	{
		List<Capability> capabilityList= capabilityRepository.findByArtifactId(artifactId);
		Capability capability= new Capability();
		if(capabilityList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			for(int i=0;i<capabilityList.size();i++)
			{
				capability=capabilityList.get(i);
				capability.setStatus("Deleted");
				Query select= Query.query(Criteria.where("artifactId").is(capability.getArtifactId()));
				Update update= new Update();
				update.set("status",capability.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, Capability.class);
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

	@GetMapping("/capabilities/{tenantId}/{status}")
	public ResponseEntity<List<Capability>> getCapabilityByStatus(@PathVariable(value="tenantId") int tenantId,@PathVariable(value="status") String status)
	{
		List<Capability> capabilityList= capabilityRepository.findByStatus(status,tenantId);
		if(capabilityList.isEmpty())
		{
			return new ResponseEntity<List<Capability>>(capabilityList,HttpStatus.NO_CONTENT);
			
		}
		else
			return new ResponseEntity<List<Capability>>(capabilityList,HttpStatus.OK);
		}
	
	@PutMapping("/approval/capabilities/{artifactId}/{status}")
	public ResponseEntity<Void> updateCapabilityStatus(@PathVariable(value="artifactId") int artifactId,@PathVariable(value="status") String status)
	{
		Artifact artifact=artifactRepository.findById(artifactId).get();
		artifact.setStatus(status);
		List<Capability> capabilityList= capabilityRepository.findByArtifactId(artifactId);
		Capability capability= new Capability();
		if(capabilityList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			
				capability=capabilityList.get(0);
				capability.setStatus(status);
				Query select= Query.query(Criteria.where("artifactId").is(capability.getArtifactId()));
				Update update= new Update();
				update.set("status",capability.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, Capability.class);
				artifactRepository.save(artifact);
			
	}
	
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
	

			
}