package com.insurance.service;

import java.util.List;

import com.insurance.entity.InsurancePlan;

import jakarta.servlet.http.HttpServletResponse;

	public interface PlanService {

		 InsurancePlan  savePlan(InsurancePlan plan);
	     List<InsurancePlan> getAllPlans();
	     List<String> getPlanName();
	     List<String> getPlanStatus();
	     public boolean generateExcel(HttpServletResponse response)throws Exception;
	     boolean generatePdf(HttpServletResponse response) throws Exception;
	}


