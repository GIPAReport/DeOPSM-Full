package com.designops.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.designops.exception.ArtifactAlreadyExistsException;
import com.designops.model.Artifact;
//import com.designops.model.CMS;
import com.designops.model.TenantUser;
//import com.designops.repository.ArtifactElasticSearchRepository;
import com.designops.repository.ArtifactRepository;
import com.designops.repository.RoleRepository;
import com.designops.repository.TenantRepository;
import com.designops.repository.TenantUserRepository;
import com.designops.repository.UsersRepository;
import com.designops.utility.Constant;
import com.designops.utility.Email;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.designops.utility.*;

@RestController
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class, basePackages = {
		"com.designops" })
public class ArtifactController implements Serializable {

	@Autowired
	ArtifactRepository artifactRepository;

	@Autowired
	private RestTemplate restTemplate;


	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime currentDate = LocalDateTime.now();

	@Autowired
	TenantRepository tenantRepository;

	@Autowired
	UsersRepository userRepository;

	ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger(ArtifactController.class);

//
//	@Autowired
//	ArtifactElasticSearchRepository artifactElasticSearchRepository;

//	private ElasticSearchOperations elasticsearchOperations;

	
	/*
	@GetMapping("/findByDesc/{desc}")
	public List<CMS> findByArtifactDescription(@PathVariable String desc)
	
	{
	//	List<CMS> foundSearchResults= artifactElasticSearchRepository.findByPageDescriptionOrUserNameOrArtifactTypeOrPageTitleOrTaskIdOrArtifactCategoryContaining(desc,desc,desc,desc,desc,desc);
		List<CMS> foundSearchResults=artifactElasticSearchRepository.findByPageDescriptionOrUserNameOrArtifactTypeOrPageTitleOrTaskidorArtifactCategoryContaining(desc, desc, desc, desc, desc, desc);
	//	List<CMS> foundSearchResults= artifactElasticSearchRepository.findByPageDescriptionOrUserNameOrArtifactTypeOrPageTitleOrTaskIdOrArtifactCategoryContaining(desc,desc,desc,desc,desc,desc);
		
		List<CMS> returnSearchResults= new ArrayList<>();
		
		for(int i=0;i<foundSearchResults.size();i++)
		{
			CMS foundSearch=foundSearchResults.get(i);
			
			if(foundSearch.getStatus().equalsIgnoreCase("Approved"));
			
			{
				returnSearchResults.add(foundSearch);
			}
		}
		return returnSearchResults;
		
		
		
	}
	
	
	*/
	
	@GetMapping("/artifactList/{userName:[a-zA-Z0-9 _]*}")
	public ResponseEntity<List<Artifact>> getAllArtifactByUserName(@PathVariable(value="userName") String userName)
	{
		List<Artifact> existingArtifactList= artifactRepository.findAllByUserName(userName);
		List<Artifact> artifactList= new ArrayList<Artifact>();
		
		if(!existingArtifactList.isEmpty())
		{
			for(int i=0;i<existingArtifactList.size();i++ )
			{
				if(existingArtifactList.get(i).isActive())
				{
					artifactList.add(existingArtifactList.get(i));
				}
			}
		
		
		Collections.sort(artifactList,new Comparator<Artifact>() {
			public int compare(Artifact o1,Artifact o2)
			{
				return o2.getLastModifiedOn().compareTo(o1.getLastModifiedOn());
			}
		});
				return new ResponseEntity<List<Artifact>>(artifactList,HttpStatus.OK);
				
	}
	else
		
		return new ResponseEntity<List<Artifact>>(HttpStatus.NO_CONTENT);
	}

	

	@PostMapping(value="/uploadToArtifactory/{tenantName}/{artifactCategory}")
	public ResponseEntity<List<String>> uploadToArtifactory(@PathVariable(value="tenantName") String tenantName,@PathVariable(value="artifactCategory") String artifactCategory,@RequestParam("file") MultipartFile[] files) throws UnsupportedEncodingException
	{
		ArrayList<String> artifactoryURLList= new ArrayList<String>();
		
		String artifactoryURL="";
		
		Artifactory artifactory = createArtifactory(Constant.odysseyUserName, Constant.odysseyPassword, Constant.artifactURL);
		if(artifactory==null)
		{
			throw new IllegalArgumentException("Unable to create Artifactory");
			
		}
		
		for(MultipartFile file:files)
		{
			String fileName= URLEncoder.encode(file.getOriginalFilename(),"UTF-8");
			
			try
			{
				String tenant=URLEncoder.encode(tenantName,"UTF-8");
				String artifact=URLEncoder.encode(artifactCategory,"UTF-8");
				
				artifactory.repository(Constant.repoName).upload(tenant+"/"+artifact+"/"+fileName, file.getInputStream()).doUpload();
				
				artifactoryURL=Constant.artifactURL+Constant.repoName+tenant+"/"+artifact+"/"+fileName;
				
				artifactoryURLList.add(artifactoryURL);
			}
			catch(Exception exception)
			{
				logger.info("Exception in uploading the file to artifactory",exception);
				exception.printStackTrace();
			}
		}
		
		if(!artifactoryURLList.isEmpty())
		{
			return new ResponseEntity<List<String>>(artifactoryURLList,HttpStatus.ACCEPTED);
		}
		
		else
		{
			throw new ArtifactAlreadyExistsException("File already exist, please upload different file");
		}
		
}
	
	private static Artifactory createArtifactory(String username, String password, String artifactoryUrl)
	{
		if(StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)||StringUtils.isEmpty(artifactoryUrl))
		{
			throw new IllegalArgumentException("Credentials passed to Artifactory are not valid");
		}
		return ArtifactoryClientBuilder.create().setUrl(artifactoryUrl).setUsername(username).setPassword(password).build();
	}
	
	@PostMapping("/postElastic/{index}/{docName}/{id}")
	public void insertElasticSearch(@PathVariable String index, @PathVariable String docName, @PathVariable String id,@RequestBody String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url= Constant.elasticURL+index+"/" + docName+"/" +id;
		HttpEntity<String> request= new HttpEntity<String>(json,headers);
		restTemplate.postForEntity(url, request, Void.class);
		
		
	}
	
	@Autowired
	public ArtifactController(RestTemplateBuilder builder)
	{
		this.restTemplate=builder.build();
	}
	
	ArrayList<String> fileList= new ArrayList<String>();
	
	@GetMapping("/artifact/{tenantId}")
	public ResponseEntity<List<Artifact>> getAllArtifactsByTenantId(@PathVariable (value="tenantId") Integer tenantId)
	{
		List<Artifact> existingArtifactList=artifactRepository.findAllByTenantOrderByLastModifiedOnAsc(tenantId);
	//	List<Artifact> existingArtifactList= artifactRepository.findAllByTenantIdOrderByLastModifiedOnAsc(tenantId);
		List<Artifact> artifactList= new ArrayList<Artifact>();
		if(!existingArtifactList.isEmpty())
		{
			for(int i=0;i<existingArtifactList.size();i++)
			{
				if(existingArtifactList.get(i).isActive())
				{
					artifactList.add(existingArtifactList.get(i));
				}
			}
			return new ResponseEntity<List<Artifact>>(artifactList,HttpStatus.OK);
		}
		
		else
		 return new ResponseEntity<List<Artifact>>(HttpStatus.NO_CONTENT);
				
	}
	
	@GetMapping("/artifacts")
	public ResponseEntity<List<Artifact>> getAllArtifacts()
	{
		List<Artifact> existingArtifactList= artifactRepository.findAll();
		List<Artifact> artifactList= new ArrayList<Artifact>();
		if(!existingArtifactList.isEmpty())
		{
			for(int i=0;i<existingArtifactList.size();i++)
			{
				if(existingArtifactList.get(i).isActive())
				{
					artifactList.add(existingArtifactList.get(i));
				}
			}
			return new ResponseEntity<List<Artifact>>(artifactList,HttpStatus.OK);
		}
		
		else
		 return new ResponseEntity<List<Artifact>>(HttpStatus.NO_CONTENT);
				
	}

	@GetMapping("/artifacts/{artifactId}")
	public ResponseEntity<Artifact> getArtifactsById(@PathVariable (value="artifactId") Integer artifactId)
	{
		Artifact foundArtifact=artifactRepository.findById(artifactId).get();
		if(foundArtifact==null || !foundArtifact.isActive())
		{
			
			return new ResponseEntity<Artifact>(foundArtifact,HttpStatus.OK);
		}
		
		else
		 return new ResponseEntity<Artifact>(HttpStatus.NO_CONTENT);
				
	}
	
	static void deleteFolder(File file)
	{
		for(File subFile: file.listFiles())
		{
			if(subFile.isDirectory())
			{
				deleteFolder(subFile);
			}
			else
			{
				subFile.delete();
			}
		}
		file.delete();
	}


@PostMapping(value="/uploadToGIT")
public ResponseEntity<List<String>> uploadToGit(@RequestParam("file") MultipartFile[] inputFiles) throws NoFilepatternException, GitAPIException, IOException
{
	ArrayList<String> bitbucketList= new ArrayList<String>();
	
	try(Repository repository = CreateRepository.createNewRepository())
	{
		File localPath= File.createTempFile("TestGitRepository", "",new File(Constant.uploadTempFolder));
		
		if(!localPath.delete())
		{
			throw new IOException("Could nott delete temporary file"+localPath);
			
		}
		
		
		String REMOTE_URL= Constant.bitbucketURL;
		CredentialsProvider cp= new UsernamePasswordCredentialsProvider(Constant.odysseyUserName, Constant.odysseyPassword);
		
		try(Git result = Git.cloneRepository().setBranch(Constant.gitBranch).setURI(REMOTE_URL).setDirectory(localPath).setCredentialsProvider(cp).call())
		{
			try(Git git= new Git(result.getRepository())){
				for(MultipartFile inputFile : inputFiles)
				{
					File myFile = new File(git.getRepository().getDirectory().getParent(),inputFile.getOriginalFilename());
						
						inputFile.transferTo(myFile);
						String fileName= inputFile.getOriginalFilename();
						git.add().addFilepattern(fileName).call();
						
						git.commit().setAll(true).setMessage(Constant.gitCommitMessage).call();
						
						org.eclipse.jgit.lib.ObjectId lastCommitId= git.getRepository().resolve(Constants.HEAD);
						String commitId= lastCommitId.toString().replace("AnyObjectId[","").replace("]","");
						String bitbucketURL= Constant.downloadBitbucketURL+fileName+"?at="+commitId;
						try
						{
							
							git.push().setRemote("origin").setCredentialsProvider(cp).setRefSpecs(new RefSpec(Constant.gitBranch+":"+Constant.gitBranch)).call();
						}
						catch(Exception e)
						{
							e.printStackTrace();
					
						}
						
						deleteFolder(localPath);
						bitbucketList.add(bitbucketURL);
							}
				}
			}
		}
		return new ResponseEntity<List<String>>(bitbucketList,HttpStatus.CREATED);
	}
}
	

