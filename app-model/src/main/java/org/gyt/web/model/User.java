package org.gyt.web.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * 用户实体
 * Created by y27chen on 2016/7/12.
 */
@Entity
@Table
public class User implements UserDetails {

    @Id
    private String username;

    private boolean locked;

    private boolean disabled;

    private String password;

    private String nickname;

    private String telephone;

    private String email;

    /**
     * 用户真实姓名
     */
    private String name;

    /**
     * 用户性别
     * 0 - 未知
     * 1 - 男
     * 2 - 女
     */
    private Integer sex;

    private String address;

    private Date createdDate = new Date();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        if (this.isAccountNonLocked()) {
            roles.forEach(role -> role.getAuthorities().forEach(authority -> grantedAuthorityList.add(new SimpleGrantedAuthority(authority))));
        }

        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", locked=" + locked +
                ", disabled=" + disabled +
                ", nickname='" + nickname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username != null ? username.equals(user.username) : user.username == null;

    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    public String getDisplayName() {
        if (!StringUtils.isEmpty(nickname)) {
            return nickname;
        }

        return username;
    }
}
