package org.gyt.web.model.user;

import org.gyt.web.model.project.Project;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体
 * Created by y27chen on 2016/7/12.
 */
@Entity
@Table
public class User implements UserDetails {

    @Id
    private String account;

    @Min(8)
    private String password;

    @Min(8)
    private String passwordConfirm;

    @NotEmpty
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

    /**
     * 用户管理的团契
     */
    @ManyToMany()
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_account", referencedColumnName = "account"),
            inverseJoinColumns = @JoinColumn(name = "project_name", referencedColumnName = "projectName")
    )
    private Set<Project> hostProjects = new HashSet<Project>();

    public User() {
    }

    public User(String account) {
        this.account = account;
    }

    public User(String account, String nickname) {
        this.account = account;
        this.nickname = nickname;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Set<Project> getHostProjects() {
        return hostProjects;
    }

    public void setHostProjects(Set<Project> hostProjects) {
        this.hostProjects = hostProjects;
    }
}
