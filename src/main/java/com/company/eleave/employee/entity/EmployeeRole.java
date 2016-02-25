package com.company.eleave.employee.entity;

import com.company.eleave.BaseEntity;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "employee_role", uniqueConstraints = {
    @UniqueConstraint(columnNames = "role_name")})
public class EmployeeRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "role_name", length = 30)
    private String roleName;

    @Column(name = "comment", length = 255)
    private String comment;

    @ManyToMany
    @JoinTable(name = "employee_role_privilege", joinColumns = {
        @JoinColumn(name = "employee_role_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "privilege_id", referencedColumnName = "id")})
    private Set<Privilege> privileges;

    public void addPrivilege(Privilege privilege) {
        if (this.privileges == null) {
            this.privileges = new HashSet<>();
        }
        this.privileges.add(privilege);
    }

    public Set<Privilege> getPrivileges() {
        return privileges == null ? new HashSet<>() : privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        if (this.privileges == null) {
            this.privileges = Sets.newHashSet(privileges);
        } else {
            this.privileges.addAll(privileges);
        }
    }

    public String getName() {
        return roleName;
    }

    public void setName(String name) {
        this.roleName = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
