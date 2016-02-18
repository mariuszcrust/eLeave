package com.company.eleave.employee.entity;

import com.company.eleave.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
public class Employee extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    private String email;

    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private EmployeeRole employeeRole;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public EmployeeRole getEmployeeRole() {
		return employeeRole;
	}
	
	public void setEmployeeRole(final EmployeeRole employeeRole) {
		this.employeeRole = employeeRole;
	}

}
