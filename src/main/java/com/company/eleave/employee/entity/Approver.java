package com.company.eleave.employee.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author Sebastian Szlachetka
 */
public class Approver implements Serializable {

    @OneToOne
    @PrimaryKeyJoinColumn
    private Employee employee;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id")
    private Set<Employee> approvers;

    private Date startDate;
    private Date endDate;

}
