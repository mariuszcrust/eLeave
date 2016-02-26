package com.company.eleave.security.entity;

import com.company.eleave.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "privilege")
public class Privilege extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "privilege_name")
    private PrivilegeName privilegeName;

    public PrivilegeName getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(PrivilegeName privilegeName) {
        this.privilegeName = privilegeName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).append(getPrivilegeName()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Privilege other = (Privilege) obj;
        return new EqualsBuilder().append(getId(), other.getId()).append(getVersion(), other.getVersion()).append(getPrivilegeName(), other.getPrivilegeName()).isEquals();
    }

    @Override
    public String toString() {
        return "Privilege{" + "name=" + privilegeName + '}';
    }

    public enum PrivilegeName {
        REQUEST_LEAVE, APPROVED_LEAVE, ADD_EMPLOYEE, ADD_LEAVE_TYPE, REMOVE_LEAVE_TYPE, REMOVE_EMPLOYEE;
    }
}
