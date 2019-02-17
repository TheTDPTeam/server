package com.tdpteam.repo.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tdpteam.repo.entity.base.BaseEntityAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = false, exclude = {"student", "teacher"})
@ToString(exclude = {"student", "teacher"})
@Data
@Entity
@Table(name = "account")
public class Account extends BaseEntityAudit implements UserDetails {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account", optional = false, orphanRemoval = true)
    private UserDetail userDetail;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
    private Student student;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Role role;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return getEmail();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
