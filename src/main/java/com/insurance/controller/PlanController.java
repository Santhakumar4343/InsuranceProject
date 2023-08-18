package com.insurance.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.entity.InsurancePlan;
import com.insurance.repository.PlanRepository;
import com.insurance.service.PlanService;

import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/plans")
@CrossOrigin("*")
public class PlanController {
    @Autowired
    private  PlanService planService;
    @Autowired
    PlanRepository planRepo;
    @GetMapping("/getAll")
    public List<InsurancePlan> getAllPlans() {
        return planService.getAllPlans();
    }
    @PostMapping("/savePlan")
    public InsurancePlan createPlan(@RequestBody InsurancePlan plan) {
        return planService.savePlan(plan);
    }
    @GetMapping("/getPlanNames")
    public List<String> getplanNames() {
    	return planService.getPlanName();
    }
    @GetMapping("/getPlanStatus")
    public List<String> getplanStatus() {
    	return planService.getPlanStatus();
    }
    
    @GetMapping("/excel")
	public void generateExcelReport(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=courses.xls";
		response.setHeader(headerKey, headerValue);
		planService.generateExcel(response);
		response.flushBuffer();
	}
    @GetMapping("/pdf")
    public void generatePdf(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=courses.pdf";
        response.setHeader(headerKey, headerValue);

        planService.generatePdf(response);

        response.flushBuffer();
    }


}
	

