package com.designops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.designops.model. SystemParameter;
//import antir.collections.List;
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Integer>
{
SystemParameter findByParamkey (String paramkey);
}
