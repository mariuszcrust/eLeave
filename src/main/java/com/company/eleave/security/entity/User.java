package com.company.eleave.security.entity;

import com.company.eleave.BaseEntity;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "password")
  private String password;

  @ManyToMany
  @JoinTable(name = "user_user_role",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "user_role_id", referencedColumnName = "id")})
  private Set<UserRole> userRoles;

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<UserRole> getUserRoles() {
    if (userRoles == null) {
      userRoles = new HashSet<>();
    }
    return Collections.unmodifiableSet(userRoles);
  }

  public void setUserRoles(Set<UserRole> userRoles) {
    Set<UserRole> anUserRoles = Objects.requireNonNull(userRoles);
    if (this.userRoles == null) {
      this.userRoles = Sets.newHashSet(anUserRoles);
    } else {
      this.userRoles.addAll(anUserRoles);
    }
  }

  public void addUserRole(UserRole userRole) {
    UserRole anUserRole = Objects.requireNonNull(userRole);
    if (this.userRoles == null) {
      this.userRoles = new HashSet<>();
    }
    this.userRoles.add(anUserRole);
  }
  
  public Set<Privilege> getPermissions() {
    return getUserRoles().stream().map(r -> r.getPrivileges()).flatMap(p -> p.stream()).collect(Collectors.toSet());
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
      return Stream.concat(getUserRoles().stream(), getPermissions().stream()).collect(Collectors.toSet());
  }

  @Override
  public String getUsername() {
    return userName;
  }

  //TODO check do we need it
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  //TODO check do we need it
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  //TODO check do we need it
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  //TODO check do we need enabled
  @Override
  public boolean isEnabled() {
    return true;
  }

}
