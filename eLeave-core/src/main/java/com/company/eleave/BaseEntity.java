package com.company.eleave;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author Sebastian Szlachetka
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    public Long getId() {
        return id;
    }
    
    public void setId(final long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }

    @Override
    public String toString() {
        return "BaseEntity{" + "id=" + id + '}';
    }
}
