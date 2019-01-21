package com.tdpteam.repo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "version")
    @Version
    private Long version;

    @Override
    public int hashCode() {
        return (this.getId() != null ? this.getId().hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;

        BaseEntity other = (BaseEntity) obj;
        return Objects.equals(this.getId(), other.getId()) || (this.getId() != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [ID=" + id + "]";
    }
}
