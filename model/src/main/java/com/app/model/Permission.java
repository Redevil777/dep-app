package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by andrei on 15.09.16.
 */
@Entity
@Table(name = "permissions")
public class Permission implements GrantedAuthority{

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "PERMISSIONNAME")
    private String permissionName;

    @JsonIgnore
    @OneToMany
    @JoinTable(name = "role_permissions",
                joinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> permRoles;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return permissionName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Set<Role> getPermRoles() {
        return permRoles;
    }

    public void setPermRoles(Set<Role> permRoles) {
        this.permRoles = permRoles;
    }

    public Permission() {
    }

    public Permission(String permissionName){
        this.permissionName = permissionName;
    }

    public Permission(String permissionName, Set<Role> permRoles) {
        this.permissionName = permissionName;
        this.permRoles = permRoles;
    }
}
