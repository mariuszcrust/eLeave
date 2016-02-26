package com.company.eleave.security.entity;

import com.company.eleave.BaseEntity;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "user_role", uniqueConstraints = {
    @UniqueConstraint(columnNames = "role_name")})
public class UserRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 30)
    private RoleName roleName;

    @Column(name = "comment", length = 255)
    private String comment;

    @ManyToMany
    @JoinTable(name = "user_role_privilege", joinColumns = {
        @JoinColumn(name = "user_role_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "privilege_id", referencedColumnName = "id")})
    private Set<Privilege> privileges;

    public void addPrivilege(Privilege privilege) {
        Privilege aPrivilege = Objects.requireNonNull(privilege);
        if (this.privileges == null) {
            this.privileges = new HashSet<>();
        }
        this.privileges.add(aPrivilege);
    }

    public Set<Privilege> getPrivileges() {
        if (privileges == null) {
            privileges = new HashSet<>();
        }
        return Collections.unmodifiableSet(privileges);
    }

    public void setPrivileges(Set<Privilege> privileges) {
        Set<Privilege> aPrivileges = Objects.requireNonNull(privileges);
        if (this.privileges == null) {
            this.privileges = Sets.newHashSet(aPrivileges);
        } else {
            this.privileges.addAll(aPrivileges);
        }
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public enum RoleName {

        SUPER_USER, HR, APPROVER, EMPLOYEE;
    }
}
