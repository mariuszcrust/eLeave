package com.company.eleave.employee.entity;

import com.company.eleave.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = EmployeeRole.MODEL_NAME, uniqueConstraints = {
    @UniqueConstraint(columnNames = "id"),
    @UniqueConstraint(columnNames = "role_name")})
public class EmployeeRole extends BaseEntity implements Serializable {

    public static final String MODEL_NAME = "ROLE";
    private static final long serialVersionUID = 1L;

    @Column(name = "role_name", length = 30)
    private String roleName;

    @Column(name = "comment", length = 255)
    private String comment;
}
