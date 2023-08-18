package com.insurance.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InsurancePlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String planName;
	private String planStatus;
	private String gender;
	private Date startDate;
	private Date endDate;

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public InsurancePlan(Long id, String planName, String planStatus, String gender, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.planName = planName;
		this.planStatus = planStatus;
		this.gender = gender;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public InsurancePlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "InsurenceProject [id=" + id + ", planName=" + planName + ", planStatus=" + planStatus + ", gender="
				+ gender + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
