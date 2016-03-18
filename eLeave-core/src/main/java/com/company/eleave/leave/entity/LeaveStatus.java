package com.company.eleave.leave.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author Sebastian Szlachetka
 */
@Embeddable
public class LeaveStatus {

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private StatusName statusName;

    private String comment;

    public StatusName getStatusName() {
        return statusName;
    }

    public void setStatusName(StatusName statusName) {
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
        return new HashCodeBuilder().append(getComment()).append(getStatusName()).toHashCode();
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
                .append(getStatusName(), other.getStatusName())
                .append(getComment(), other.getComment()).isEquals();
    }

    @Override
    public String toString() {
        return "LeaveStatus{statusName=" + statusName + ", comment=" + comment + '}';
    }

    public enum StatusName {
        PENDING, APPROVED, REJECTED, WITHDRAW;

        public static boolean contains(String test) {

            for (StatusName c : StatusName.values()) {
                if (c.name().equals(test)) {
                    return true;
                }
            }

            return false;
        }
    }
}
