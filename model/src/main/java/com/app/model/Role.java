package com.app.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by andrei on 15.09.16.
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rolename")
    private String roleName;

    @OneToMany
    @JoinTable(name = "role_permissions",
                joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<Permission> permissions;

    @OneToMany
    @JoinTable(name = "user_roles",
                joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> userRoles;

    public Role() {
    }

    public Role(int id, String roleName){
        this.id = id;
        this.roleName = roleName;
    }

    public Role(String roleName, Set<Permission> permissions, Set<User> userRoles) {
        this.roleName = roleName;
        this.permissions = permissions;
        this.userRoles = userRoles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<User> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
