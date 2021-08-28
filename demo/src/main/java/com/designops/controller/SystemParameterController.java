//package com.designops.controller;
//
//import java.util. HashMap;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation. Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind. annotation. DeleteMapping;
//import org.springframework.web.bind. annotation. GetMapping;
//import org.springframework.web.bind. annotation.PathVariable;
//import org.springframework.web.bind. annotation.PostMapping;
//import org.springframework.web.bind. annotation.PutMapping;
//import org.springframework.web.bind. annotation.RequestBody;
//import org.springframework.web.bind. annotation.RestController;
//import com.designops.exception. ParameterNotFoundException;
//import com.designops.exception.UserNotFoundException;
//import com.designops.model.SystemParameter;
//import com.designops.repository. SystemParameterRepository;
//
//@RestController
//public class SystemParameterController {
//	
//
//@Autowired
//private SystemParameterRepository systemParameterRepository;
//
//@GetMapping("/systemparameters")
//public ResponseEntity<List<SystemParameter>> getAllParameters() {
//return new ResponseEntity<List<SystemParameter>>(systemParameterRepository.findAll(), HttpStatus.OK);
//}
//
//
//@PostMapping("/systemparameters")
//public ResponseEntity<SystemParameter> addParameters (@RequestBody SystemParameter systemParameters) 
//{
//   SystemParameter newSystemParameter = new SystemParameter();
////   newSystemParameter.setParamkey(systemParameters.getParamkey());
//   newSystemParameter.setParamvalue(systemParameters.getParamvalue());
//   SystemParameter insertedParameter = systemParameterRepository.save (newSystemParameter);
//   return new ResponseEntity <SystemParameter>(insertedParameter, HttpStatus.OK);
//}
//
//public ResponseEntity<SystemParameter> getSystemParameter(@PathVariable(value = "key") String key)
//{
//SystemParameter systemParameter=systemParameterRepository.findByParamkey (key);
//return new ResponseEntity<SystemParameter>(systemParameter, HttpStatus.OK);
//}
//
//
//@PutMapping("/systemparameters/{id}")
//public ResponseEntity<SystemParameter> updateParameters (@Pathvariable(value = "id") Integer paramid,
//@RequestBody SystemParameter systemParameters) {
//=
//SystemParameter sysparam systemParameterRepository.findById(paramid).get();
//if (sysparam!=null) {
//sysparam.setParamkey (systemParameters.getParankey();
//sysparam.setParamid(systemParameters.getParamid();
//SystemParameter insertedParameter = systemParameterRepository.save(sysparam);
//return new ResponseEntity<SystemParameter>(insertedParameter, HttpStatus.OK);
//} else {
//throw new ParameterNotFoundException ("No parameter found with this id="+paramid);
//}
//}
//
//
//
//@DeleteMapping("/systemparameters/{id}")
//public ResponseEntity<Map<String, Boolean>> deleteParameters(@Pathvariable(value = "id") Integer paramid) {
//SystemParameter sysparam = systemParameterRepository.findById(paramid).orElse Throw() -> new UserNotFoundException("User not
//systemParameterRepository.deleteById(paramid);
//Map<String, Boolean> response = new HashMap<>();
//response.put("deleted", Boolean. TRUE);
//return new ResponseEntity<Map<String, Boolean>>(response, HttpStatus. ACCEPTED);
//}
//
//
//}
