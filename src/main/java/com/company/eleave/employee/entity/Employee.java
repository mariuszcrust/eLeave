package com.company.eleave.employee.entity;

import com.company.eleave.BaseEntity;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.security.entity.User;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;
    
    @OneToMany(mappedBy = "employee")
    private List<AnnualBalanceLeave> annualBalanceLeave;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public List<AnnualBalanceLeave> getAnnualBalanceLeave() {
        return annualBalanceLeave;
    }

    public void setAnnualBalanceLeave(List<AnnualBalanceLeave> annualBalanceLeave) {
        this.annualBalanceLeave = annualBalanceLeave;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
