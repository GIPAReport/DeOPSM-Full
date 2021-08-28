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
import com.designops.model.LibraryForm;
import com.designops.model.Resource;
import com.designops.model.RichTextBlock;
import com.designops.model.Tenant;
import com.designops.model.Users;
import com.designops.repository.ArtifactRepository;
import com.designops.repository.LibraryRepository;
import com.designops.repository.TenantRepository;
import com.designops.repository.UsersRepository;
import com.designops.utility.Constant;
import com.mongodb.client.result.UpdateResult;

public class LibraryController {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime currentDate = LocalDateTime.now();
	

	@Autowired
	LibraryRepository libraryRepository;
	
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
	
	@PostMapping("/libraries")
	public ResponseEntity<LibraryForm> addLibrary(@RequestBody LibraryForm library)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		
		String modifiedDate= formatter.format(date);
		
		List<LibraryForm> existingLibraryFormList= libraryRepository.findByPageTitle(library.getPageTitle());
		if(existingLibraryFormList.isEmpty())
		{
			List<String> taskIdList= library.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= new Artifact();
			artifact.setArtifactCategory("Libraries");
			artifact.setArtifactDescription(library.getPageDescription());
			artifact.setArtifactTitle(library.getPageTitle());
			artifact.setArtifactType(library.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setCreatedOn(currentDate);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(library.getTenantId());
			artifact.setUserId(library.getUserId());
			artifact.setCreatedBy(library.getUserName());
			artifact.setStatus(library.getStatus());
			artifact.setModifiedBy(library.getUserName());
			
			int tenantId= library.getTenantId();
			
			String userName= library.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Libraries"))
			{
			return new  ResponseEntity<LibraryForm>(HttpStatus.NO_CONTENT);
			}
		
			else
			{
			
			artifact.setTenantName(tenant.getTenantName());
			Artifact insertedArtifact= artifactRepository.save(artifact);
			
			library.setArtifactId(insertedArtifact.getArtifactId());
			library.setLastModifiedDate(modifiedDate);
			library.setArtifactCategory("Libraries");
			library.setTenantName(artifact.getTenantName());
			library.setVersion(1);
			libraryRepository.save(library);
			
			return new ResponseEntity<LibraryForm>(library,HttpStatus.CREATED);
			}
			
		}
		else
			throw new ArtifactAlreadyExistsException("Artifact already exists::" +library.getPageTitle());
				

	}
	
	@PutMapping(value="/libraries")
	public ResponseEntity<LibraryForm> updateLibraryForm(@RequestBody LibraryForm library)
	{
		LocalDateTime currentDate= LocalDateTime.now();
		String modifiedDate= formatter.format(date);
		
		List<LibraryForm> existingLibraryFormList= libraryRepository.findByArtifactId(library.getArtifactId());
		
		LibraryForm existingLibraryForm=null;
		
		if(existingLibraryFormList.isEmpty())
		{
			return new  ResponseEntity<LibraryForm>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			existingLibraryForm= existingLibraryFormList.get(0);
			int artifactId=existingLibraryForm.getArtifactId();
			List<String> taskIdList=library.getTaskId();
			String taskId= String.join(",", taskIdList);
			Artifact artifact= artifactRepository.findById(library.getArtifactId()).get();
			artifact.setArtifactDescription(library.getPageDescription());
			artifact.setArtifactTitle(library.getPageTitle());
			artifact.setArtifactType(library.getArtifactType());
			artifact.setTaskId(taskId);
			artifact.setActive(true);
			artifact.setLastModifiedOn(currentDate);
			artifact.setTenantId(library.getTenantId());
			artifact.setStatus(library.getStatus());
			artifact.setModifiedBy(library.getUserName());
			
			int tenantId= library.getTenantId();
			
			String userName= library.getUserName();
			
			ResponseEntity<Tenant> responseEntity= restTemplate.getForEntity(Constant.getTenantByTenantIdURL+tenantId, Tenant.class);
			
			Tenant tenant= responseEntity.getBody();
			
			ResponseEntity<Users[]> responseEntityUser= restTemplate.getForEntity(Constant.getUserByNameURL+userName, Users[].class);
			
			if(responseEntityUser.getBody()==null || tenant==null || !tenant.getArtifactCategory().contains("Libraries"))
			{
			return new  ResponseEntity<LibraryForm>(HttpStatus.NO_CONTENT);
			}
			else {
			
			Query select= Query.query(Criteria.where("artifactId").is(artifactId));
			Update update = new Update();
			update.set("version",library.getVersion());
			update.set("status",library.getStatus());
			update.set("artifactId",library.getArtifactId());
			update.set("tenantId",library.getTenantId());
			update.set("userId",library.getUserId());
			update.set("userName",library.getUserName());
			update.set("pageDescription",library.getPageDescription());
			update.set("pageTitle",library.getPageTitle());
			update.set("taskId",library.getTaskId());
			update.set("artifactType",library.getArtifactType());
			update.set("artifactCategory","Libraries");
			update.set("tenantName",library.getTenantName());
			update.set("lastModifiedDate",modifiedDate);
			update.set("libraries",library.getLibraries());
			UpdateResult updateResult= mongoTemplate.updateMulti(select,update,LibraryForm.class);
			return new ResponseEntity<LibraryForm>(library,HttpStatus.CREATED);
		}
	}
	}
	
	@GetMapping("/libraries")
	public ResponseEntity<List<LibraryForm>> getAllLibraries()
	{
		List<LibraryForm> existingLibraryFormList= libraryRepository.findAll();
		List<LibraryForm> libraryList= new ArrayList<LibraryForm>();
		LibraryForm library= null;
		if(!existingLibraryFormList.isEmpty())
		{
			for(int i=0;i<existingLibraryFormList.size();i++)
			{
			if(!existingLibraryFormList.get(i).getStatus().equalsIgnoreCase("Deleted"))
			{
				library=existingLibraryFormList.get(i);
				libraryList.add(library);
			}
			}
			return new ResponseEntity<List<LibraryForm>>(libraryList,HttpStatus.OK);
		}
		else
			return new ResponseEntity<List<LibraryForm>>(HttpStatus.NO_CONTENT);
		
			}
	
	@GetMapping("/libraries/{artifactId}")
	public ResponseEntity<LibraryForm> getLibraryFormById(@PathVariable(value="artifactId") int artifactId)
	{
		List<LibraryForm> libraryList= libraryRepository.findByArtifactId(artifactId);
		LibraryForm library= new LibraryForm();
		if(libraryList.isEmpty())
		{
			return new ResponseEntity<LibraryForm>(HttpStatus.NO_CONTENT);
			
		}
		else
			if(libraryList.size()==1)
			{
			if(!libraryList.get(0).getStatus().equalsIgnoreCase("Deleted"))
			{
				library=libraryList.get(0);
			}
			else
				return new ResponseEntity<LibraryForm>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<LibraryForm>(library,HttpStatus.OK);
		}
		
	
	@DeleteMapping("/libraries/{artifactId}")
	public ResponseEntity<Void> deleteLibraryForm(@PathVariable(value="artifactId") int artifactId)
	{
		List<LibraryForm> libraryList= libraryRepository.findByArtifactId(artifactId);
		LibraryForm library= new LibraryForm();
		if(libraryList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			for(int i=0;i<libraryList.size();i++)
			{
				library=libraryList.get(i);
				library.setStatus("Deleted");
				Query select= Query.query(Criteria.where("artifactId").is(library.getArtifactId()));
				Update update= new Update();
				update.set("status",library.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, LibraryForm.class);
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

	@GetMapping("/libraries/{tenantId}/{status}")
	public ResponseEntity<List<LibraryForm>> getLibraryFormByStatus(@PathVariable(value="tenantId") int tenantId,@PathVariable(value="status") String status)
	{
		List<LibraryForm> libraryList= libraryRepository.findByStatus(status,tenantId);
		if(libraryList.isEmpty())
		{
			return new ResponseEntity<List<LibraryForm>>(libraryList,HttpStatus.NO_CONTENT);
			
		}
		else
			return new ResponseEntity<List<LibraryForm>>(libraryList,HttpStatus.OK);
		}
	
	@PutMapping("/approval/libraries/{artifactId}/{status}")
	public ResponseEntity<Void> updateLibraryFormStatus(@PathVariable(value="artifactId") int artifactId,@PathVariable(value="status") String status)
	{
		Artifact artifact=artifactRepository.findById(artifactId).get();
		artifact.setStatus(status);
		List<LibraryForm> libraryList= libraryRepository.findByArtifactId(artifactId);
		LibraryForm library= new LibraryForm();
		if(libraryList.isEmpty())
		{
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		}
		else
		{
			
				library=libraryList.get(0);
				library.setStatus(status);
				Query select= Query.query(Criteria.where("artifactId").is(library.getArtifactId()));
				Update update= new Update();
				update.set("status",library.getStatus());
				UpdateResult updateResult= mongoTemplate.updateMulti(select, update, LibraryForm.class);
				artifactRepository.save(artifact);
			
	}
	
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
	

			
}
