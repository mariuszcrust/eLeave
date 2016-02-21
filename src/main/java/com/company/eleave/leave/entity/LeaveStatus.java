package com.company.eleave.leave.entity;

import com.company.eleave.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "leave_status")
public class LeaveStatus extends BaseEntity {

    @Column(name = "status_name")
    private String statusName;

    private String comment;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).append(getComment()).append(getStatusName()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LeaveStatus other = (LeaveStatus) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .append(getStatusName(), other.getStatusName())
                .append(getComment(), other.getComment()).isEquals();
    }

    @Override
    public String toString() {
        return "LeaveStatus{id=" + getId() + "statusName=" + statusName + ", comment=" + comment + '}';
    }

}
