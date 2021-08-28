 package com.designops.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.designops.exception.ProjectAlreadyExistsException;
import com.designops.exception.ProjectNotFoundException;
import com.designops.exception.RoleAlreadyExistsException;
import com.designops.exception.RoleNotFoundException;
import com.designops.model.Permission;
import com.designops.model.Role;
import com.designops.model.RolePermission;
import com.designops.model.Tenant;
import com.designops.model.TenantUser;
import com.designops.repository.PermissionRepository;
import com.designops.repository.RolePermissionRepository;
import com.designops.repository.RoleRepository;
import com.designops.repository.TenantUserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/marketplace")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;


	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	
	
	@PostMapping("/roles")
	public ResponseEntity<Role> addRole(@Valid @RequestBody Role roles) {
		if (roleRepository.findByRoleName(roles.getRoleName()) == null) {
			Role role = new Role();
			RolePermission rolePermission;
			role.setRoleName(roles.getRoleName());
			if (roles.getRoleDescription() != null) {
				role.setRoleDescription(roles.getRoleDescription());
			}
			role.setIsActive("1");
			//role.set

			Role insertedRole = roleRepository.save(role);
			for (int i = 0; i < roles.getRolePermission().size(); i++) {
				rolePermission = new RolePermission();
			
				rolePermission.setRoleId(role.getRoleId());
				rolePermission.setPermissionId(roles.getRolePermission().get(i).getPermissionId());
				rolePermission.setIsActive("1");
				rolePermissionRepository.save(rolePermission);
			}

			return new ResponseEntity<Role>(insertedRole, HttpStatus.CREATED);
		} else {
			throw new RoleAlreadyExistsException("Role already exists:: " + roles.getRoleName());
		}
			

	}
	
	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllRoles() throws RoleNotFoundException {

		List<Role> rolesList = roleRepository.findAll();
		List<Role> activeRoles= new ArrayList<Role>();
		if (rolesList.isEmpty()) {
			return new ResponseEntity<List<Role>>( HttpStatus.NO_CONTENT);
		}
		else
		{
			for(int i=0;i<rolesList.size();i++)
			{
				if(rolesList.get(i).getIsActive().equals("1"))
				{
					Role role = rolesList.get(i);
					List<RolePermission> rolePermissionList = rolePermissionRepository.findByRoleId(role.getRoleId());
					List<RolePermission> activeRolePermissionList = new ArrayList<>();
					for(int j=0;j<rolePermissionList.size();j++)
					{
						RolePermission rolePermission=rolePermissionList.get(j);
						if(rolePermission.getIsActive().equalsIgnoreCase("1"))
						{
							activeRolePermissionList.add(rolePermission);
						}
					}
					role.setRolePermission(activeRolePermissionList);
					activeRoles.add(role);
				}
			}
			return new ResponseEntity<List<Role>>(activeRoles, HttpStatus.OK);
		}
		
	}

		@GetMapping("/roles/{roleId}")
		public ResponseEntity<Role> getRoleByID(@PathVariable(value = "roleId") Integer roleId)
				throws RoleNotFoundException {
			Role foundrole = roleRepository.findByRoleId(roleId);
			if(foundrole.getIsActive().equals("1"))
			{
			return new ResponseEntity<Role>(foundrole, HttpStatus.OK);
			}
			else
				return  new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
		}
		
				
	

	@PutMapping("/roles/{roleId}")
	public ResponseEntity<Role> updateRole(@PathVariable("roleId") int roleId, @RequestBody Role role)
			 {
		List<Role> existingRoleList = roleRepository.findById(roleId);
		
		if (!(existingRoleList.isEmpty())) {
			Role existingRole = existingRoleList.get(0);
			existingRole.setRoleName(role.getRoleName());
			existingRole.setRoleDescription(role.getRoleDescription());
			existingRole.setIsActive("1");
			existingRole.setRoleId(roleId);
			Role updatedRole = roleRepository.save(existingRole);
			List<RolePermission> existingRolePermissionList = rolePermissionRepository.findByRoleId(roleId);
			List<RolePermission> newRolePermissionList= role.getRolePermission();
			if (!(existingRolePermissionList.isEmpty())) {
				for (int i = 0; i < existingRolePermissionList.size(); i++) {
					RolePermission foundRolePermission=existingRolePermissionList.get(i);
					foundRolePermission.setIsActive("0");
					rolePermissionRepository.save(foundRolePermission);
				}
			}
			for (int i = 0; i < newRolePermissionList.size(); i++) {
				RolePermission newRolePermission = newRolePermissionList.get(i);
				int newPermissionId= newRolePermission.getPermissionId();
				RolePermission existingRolePermission=isPermissionPresent(newPermissionId,existingRolePermissionList);
				if(existingRolePermission!=null)
				{
					existingRolePermission.setPermissionId(newRolePermission.getPermissionId());
					existingRolePermission.setIsActive("1");
					existingRolePermission.setRoleId(roleId);
					rolePermissionRepository.save(existingRolePermission);
				}
				else
				{
					RolePermission addNewRolePermission = new RolePermission();
					
					addNewRolePermission.setPermissionId(newRolePermission.getPermissionId());
					addNewRolePermission.setIsActive("1");
					addNewRolePermission.setRoleId(roleId);
					rolePermissionRepository.save(addNewRolePermission);
				}
				
			}

			return new ResponseEntity<Role>(updatedRole, HttpStatus.OK);
		} else {
			throw new RoleNotFoundException("Role not found for this role Id" +role.getRoleName());
		}
	}

	private RolePermission isPermissionPresent(int newPermissionId, List<RolePermission> existingRolePermissionList) {
		
		RolePermission existingRolePermission = null;
		for(int i=0;i<existingRolePermissionList.size();i++)
		{
			int existingPermissionId= existingRolePermissionList.get(i).getPermissionId();
			if(newPermissionId==existingPermissionId)
			{
				existingRolePermission=existingRolePermissionList.get(i);
			}
		}
		return existingRolePermission;
	}

	@Autowired
	TenantUserRepository tenantUserRepository;
	@PostMapping("/deleteRole")
	private ResponseEntity<Map<String,Boolean>> deleteMultipleRole(@RequestBody List<Integer> roleIds) throws RoleNotFoundException {
		Map<String,Boolean> response= new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		for(int k=0;k<roleIds.size();k++)
		{
			int roleId= roleIds.get(k);
			Role role= roleRepository.findByRoleId(roleId);
			if(role!=null)
			{
				List<RolePermission> rolePermissionList=rolePermissionRepository.findByRoleId(roleId);
				if(rolePermissionList.isEmpty())
				{
					role.setIsActive("0");
					roleRepository.save(role);
				}
				else
				{
					role.setIsActive("0");
					for(int i=0;i<rolePermissionList.size();i++)
					{
						rolePermissionList.get(i).setIsActive("0");
					}
					roleRepository.save(role);
				}
			}
			else
				throw new RoleNotFoundException("Role not found for this is.."+roleId);
		}
		
		return new ResponseEntity<Map<String,Boolean>>(response,HttpStatus.ACCEPTED);
			
	}
}

