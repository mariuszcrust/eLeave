package com.company.eleave.employee.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.company.eleave.BaseEntity;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
public class Approver extends BaseEntity implements Serializable {

	@OneToOne
	@PrimaryKeyJoinColumn
	private Employee employee;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id")
	private Set<Employee> approvers;

	private Date startDate;
	private Date endDate;

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
	
	public Set<Employee> getApprovers() {
		return approvers;
	}

}
