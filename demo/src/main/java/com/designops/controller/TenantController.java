package com.designops.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RestController;
import com.designops.exception.ProjectAlreadyExistsException;
import com.designops.exception.ProjectNotFoundException;
import com.designops.exception.ResourceNotFoundException;
import com.designops.exception.RoleNotFoundException;
import com.designops.exception.UserNotFoundException;
import com.designops.model.Tenant;
import com.designops.model.TenantColors;
import com.designops.model.TenantUser;
import com.designops.model.Users;
import com.designops.repository.RoleRepository;
import com.designops.repository.TenantRepository;
import com.designops.repository.TenantUserRepository;
import com.designops.repository.UsersRepository;
import com.designops.utility.Constant;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/marketplace")
public class TenantController {

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TenantUserRepository tenantUserRepository;

	@GetMapping("/tenant")
	public ResponseEntity<List<Tenant>> getAllTenant() throws ResourceNotFoundException {

		List<Tenant> tenantList = tenantRepository.findAll();
		List<Tenant> activeTenantList = new ArrayList<>();
		if (!(tenantList.isEmpty())) {
			for (int i = 0; i < tenantList.size(); i++) {
				Tenant tenant = tenantList.get(i);
				if (tenant.getIsActive().equals("1")) {
					List<TenantUser> tenantUserList = tenantUserRepository.findByTenantId(tenant.getTenantId());
					List<TenantUser> activeTenantUserList = new ArrayList<>();
					for (int j = 0; j < tenantUserList.size(); j++) {
						TenantUser tenantUser = tenantUserList.get(i);
						if (tenantUser.getIsActive().equals("1")) {
							activeTenantUserList.add(tenantUser);
						}
					}
					if (!(activeTenantUserList.isEmpty())) {
						tenant.setTenantUser(activeTenantUserList);
					}
					activeTenantList.add(tenant);
				}
			}
		} else {
			throw new ResourceNotFoundException("There are no projects present");
		}
		return new ResponseEntity<List<Tenant>>(activeTenantList, HttpStatus.OK);
	}

	@GetMapping("/tenantList")
	public ResponseEntity<List<Tenant>> getAllTenantList() throws ResourceNotFoundException {

		List<Tenant> tenantList = tenantRepository.findAll();
		List<Tenant> responseTenantList = new ArrayList<>();
		Tenant tenant;
		if (!(tenantList.isEmpty())) {
			for (int i = 0; i < tenantList.size(); i++) {
				tenant = new Tenant();
				if (tenantList.get(i).getIsActive().equals("1")) {
					tenant.setTenantId(tenantList.get(i).getTenantId());
					tenant.setTenantName(tenantList.get(i).getTenantName());
					responseTenantList.add(tenant);
				}
			}
			if (!(responseTenantList.isEmpty())) {
				return new ResponseEntity<List<Tenant>>(responseTenantList, HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("There are no projects present");
			}
		} else {

			throw new ResourceNotFoundException("There are no projects present");
		}

	}

	@GetMapping("/tenant/{id}")
	public Tenant getTenant(@PathVariable("id") int tenantId) throws ResourceNotFoundException {

		return tenantRepository.findById(tenantId)
				.orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + tenantId));
	}

	@PostMapping("/addUser")
	public ResponseEntity<TenantUser> addUserToTenant(@RequestBody TenantUser tenantUser) {
		Users existinguser = usersRepository.findByEmailIgnoreCase(tenantUser.getEmail());
		if ((existinguser != null) && (existinguser.getIsActive().equals("1"))) {
			tenantUser.setUserId(existinguser.getUserId());
			tenantUser.setName(existinguser.getName());
			return new ResponseEntity<TenantUser>(tenantUser, HttpStatus.OK);
		} else {
			throw new UserNotFoundException("User not found");
		}
	}

	@PostMapping("/tenant")
	public ResponseEntity<Tenant> addTenant(@Valid @RequestBody Tenant tenant)
			throws AddressException, MessagingException {
		if (tenantRepository.findByTenantName(tenant.getTenantName()).isEmpty()) {
			Tenant newTenant = new Tenant();
			TenantUser tenantUser;
			newTenant.setTenantName(tenant.getTenantName());
			newTenant.setArtifactCategory(tenant.getArtifactCategory());
			newTenant.setTenantDescription(tenant.getTenantDescription());
			newTenant.setIsActive("1");
			Tenant insertedTenant = tenantRepository.save(newTenant);
			for (int i = 0; i < tenant.getTenantUser().size(); i++) {
				tenantUser = new TenantUser();
				tenantUser.setTenantId(newTenant.getTenantId());
				tenantUser.setUserId(tenant.getTenantUser().get(i).getUserId());
				tenantUser.setRoleId(tenant.getTenantUser().get(i).getRoleId());
				tenantUser.setEmail(tenant.getTenantUser().get(i).getEmail());
				tenantUser.setRoleName(tenant.getTenantUser().get(i).getRoleName());
				tenantUser.setName(tenant.getTenantUser().get(i).getName());
				tenantUser.setIsActive("1");
				tenantUserRepository.save(tenantUser);
			}
			return new ResponseEntity<Tenant>(insertedTenant, HttpStatus.CREATED);
		} else {
			throw new ProjectAlreadyExistsException(
					"Project already exists with this name :: " + tenant.getTenantName());
		}
	}

	@PutMapping("/tenant/{id}")
	public ResponseEntity<Tenant> updateTenant(@PathVariable("id") int tenantId, @RequestBody Tenant tenant)
			throws AddressException, MessagingException {
		List<Tenant> existingTenantList = tenantRepository.findByTenantId(tenantId);
		if (!(existingTenantList.isEmpty())) {
			Tenant existingTenant = existingTenantList.get(0);
			existingTenant.setTenantName(tenant.getTenantName());
			existingTenant.setArtifactCategory(tenant.getArtifactCategory());
			existingTenant.setTenantDescription(tenant.getTenantDescription());
			existingTenant.setIsActive("1");
			Tenant updatedTenant = tenantRepository.save(existingTenant);
			List<TenantUser> existingTenantUserList = tenantUserRepository.findByTenantId(tenantId);
			if (!(existingTenantUserList.isEmpty())) {
				for (int i = 0; i < existingTenantUserList.size(); i++) {
					int tenantUserRoleId = existingTenantUserList.get(i).getTenantUserRoleId();
					tenantUserRepository.deleteById(tenantUserRoleId);
				}
			}
			existingTenantUserList.clear();
			for (int i = 0; i < tenant.getTenantUser().size(); i++) {
				TenantUser tenantuser = tenant.getTenantUser().get(i);
				tenantuser.setIsActive("1");
				existingTenantUserList.add(tenantuser);
			}
			for (int i = 0; i < existingTenantUserList.size(); i++) {
				tenantUserRepository.save(existingTenantUserList.get(i));
			}

			return new ResponseEntity<Tenant>(updatedTenant, HttpStatus.OK);
		} else {
			throw new ProjectNotFoundException("Project not found with this name" + tenant.getTenantName());
		}
	}

	@DeleteMapping("/tenant/{id}")
	private ResponseEntity<Map<String, Boolean>> deleteTenant(@PathVariable("id") int tenantId) {
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		Tenant tenant = tenantRepository.findById(tenantId)
				.orElseThrow(() -> new ProjectNotFoundException("Tenant not found for this id :: " + tenantId));
		List<TenantUser> tenantUserList = tenantUserRepository.findByTenantId(tenantId);
		if (tenantUserList.isEmpty()) {
			tenant.setIsActive("0");
			return new ResponseEntity<Map<String, Boolean>>(response, HttpStatus.ACCEPTED);
		} else {
			tenant.setIsActive("0");
			for (int i = 0; i < tenantUserList.size(); i++) {
				tenantUserList.get(i).setIsActive("0");
			}
			return new ResponseEntity<Map<String, Boolean>>(response, HttpStatus.ACCEPTED);
		}
	}
	
	
	
	
	
	@GetMapping("/tenant/colors")
	public ResponseEntity<List<TenantColors>> getTenantColors() {
	//System.out.println("\n From controller, tenant names-" + Constant.tenantNameList);
	List<TenantColors> responseTenantList = new ArrayList<>();
	TenantColors tenantColors;
	for (int i = 0; i < Constant.tenantNameList.size(); i++) {
	tenantColors = new TenantColors();
	tenantColors.setTenantName(Constant.tenantNameList.get(i));
	tenantColors.setTenantColor(Constant.tenantColorList.get(i));
	responseTenantList.add(tenantColors);
	}
	return new ResponseEntity<List<TenantColors>>(responseTenantList, HttpStatus.OK);
	}


}
