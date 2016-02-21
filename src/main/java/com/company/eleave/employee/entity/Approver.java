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
import javax.persistence.Table;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "approver")
public class Approver extends BaseEntity implements Serializable {

	@OneToOne
	@PrimaryKeyJoinColumn
	private Employee employee;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approver_id")
	private Set<Employee> approvers;
	
	public Set<Employee> getApprovers() {
		return approvers;
	}

}
