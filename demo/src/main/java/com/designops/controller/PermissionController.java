package com.designops.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.designops.exception.PermissionAlreadyExistsException;
import com.designops.exception.PermissionNotFoundException;
import com.designops.exception.ProjectAlreadyExistsException;
import com.designops.exception.ProjectNotFoundException;
import com.designops.exception.ResourceNotFoundException;
import com.designops.exception.RoleAlreadyExistsException;
import com.designops.exception.RoleNotFoundException;
import com.designops.exception.UserNotFoundException;
import com.designops.model.Permission;
import com.designops.model.Role;
import com.designops.model.RolePermission;
import com.designops.model.TenantUser;
import com.designops.model.Users;
import com.designops.repository.PermissionRepository;
import com.designops.repository.RolePermissionRepository;
import com.designops.repository.RoleRepository;
import com.designops.repository.TenantUserRepository;
//import com.designops.repository.ProjectUserRepository;
import com.designops.repository.UsersRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PermissionController {

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@GetMapping("/permissions")
	public ResponseEntity<List<Permission>> getAllPermissions() throws ResourceNotFoundException {

		List<Permission> permissionList = permissionRepository.findAll();
		List<Permission> activePermissions= new ArrayList<Permission>();
		if (permissionList.isEmpty()) {
			throw new ResourceNotFoundException("There are no permissions present");
		}
		
			for(int i=0;i<permissionList.size();i++)
			{
				if(permissionList.get(i).getIsActive().equals("1"))
				{
					activePermissions.add(permissionList.get(i));
				}
			}
			return new ResponseEntity<List<Permission>>(activePermissions, HttpStatus.OK);
		
		
		
	}

	@GetMapping("/permissions/{permissionId}")
	public ResponseEntity<Permission> getPermission(@PathVariable("permissionId") int permissionId) throws ResourceNotFoundException {

		
		Permission foundPermission = permissionRepository.findById(permissionId).get();
		if(foundPermission.getIsActive().equals("1"))
		{
		return new ResponseEntity<Permission>(foundPermission, HttpStatus.OK);
		}
		else
			return  new ResponseEntity<Permission>(HttpStatus.NO_CONTENT);
	}


	@PostMapping("/permissions")
	public ResponseEntity<Permission> addPermission(@Valid @RequestBody Permission permission)
	{
		if (permissionRepository.findByPermissionName(permission.getPermissionName()).isEmpty()) {
			Permission perm = new Permission();
			RolePermission rolePermission;
			perm.setPermissionName(permission.getPermissionName());
			perm.setPermissionName(permission.getPermissionName());
			perm.setIsActive("1");
			Permission insertedPermission = permissionRepository.save(perm);
			for (int i = 0; i < permission.getRolePermission().size(); i++) {
				rolePermission = new RolePermission();
				rolePermission.setPermissionId(perm.getPermissionId());
				rolePermission.setRoleId(permission.getRolePermission().get(i).getRoleId());
				rolePermissionRepository.save(rolePermission);
			}

			return new ResponseEntity<Permission>(insertedPermission, HttpStatus.CREATED);
		} else {
			throw new PermissionAlreadyExistsException(
					"Permission already exists with this name :: " + permission.getPermissionName());
		}
	}

	@PutMapping("/permissions/{permissionId}")
	public ResponseEntity<Permission> updatePermission(@PathVariable("permissionId") int permissionId, @RequestBody Permission permission)
	{
		List<Permission> existingPermissionList = permissionRepository.findByPermissionId(permissionId);

		if (!(existingPermissionList.isEmpty())) {
			Permission existingPermission = existingPermissionList.get(0);
			RolePermission rolePermission;
			existingPermission.setPermissionName(permission.getPermissionName());
			existingPermission.setPermissionName(permission.getPermissionName());
			existingPermission.setIsActive("1");
			Permission updatedPermission = permissionRepository.save(existingPermission);
			for (int i = 0; i < permission.getRolePermission().size(); i++) {
				rolePermission = new RolePermission();
				rolePermission.setPermissionId(permissionId);
				rolePermission.setRoleId(existingPermission.getRolePermission().get(i).getRoleId());
				rolePermissionRepository.save(rolePermission);
			}
			return new ResponseEntity<Permission>(updatedPermission, HttpStatus.OK);

		} else {
			throw new ProjectNotFoundException("Permission not found with this name" + permission.getPermissionName());
		}

	}
	@PostMapping("/deletePermission")
	private ResponseEntity<Map<String,Boolean>> deleteMultipleRole(@RequestBody List<Integer> permissionIds) throws PermissionNotFoundException {
		Map<String,Boolean> response= new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		for(int k=0;k<permissionIds.size();k++)
		{
			int permissionId= permissionIds.get(k);
			Permission permission= permissionRepository.findById(permissionId).get();
			if(permission!=null)
			{
				List<RolePermission> rolePermissionList=rolePermissionRepository.findByPermissionId(permissionId);
				if(rolePermissionList.isEmpty())
				{
					permission.setIsActive("0");
					permissionRepository.save(permission);
				}
				else
				{
					permission.setIsActive("0");
					for(int i=0;i<rolePermissionList.size();i++)
					{
						rolePermissionList.get(i).setIsActive("0");
					}
					permissionRepository.save(permission);
				}
			}
			else
				throw new RoleNotFoundException("Permission not found for this is.."+permissionId);
		}
		
		return new ResponseEntity<Map<String,Boolean>>(response,HttpStatus.ACCEPTED);
			
	}
	} 
	
		
	
			
	
	
