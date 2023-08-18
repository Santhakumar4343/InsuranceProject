package com.insurance.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.insurance.entity.InsurancePlan;
public interface PlanRepository  extends JpaRepository<InsurancePlan, Long>{
    
	@Query("select distinct(planName) from InsurancePlan")
	public List<String> getPlanName();

	@Query("select distinct(planStatus) from InsurancePlan")
	public List<String> getPlanStatus();

}
