package org.gyt.web.model.project;

import org.gyt.web.model.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 团契实体
 * Created by AaricTech on 2016/7/26.
 */
@Entity
@Table
public class Project {

    @Id
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "project_name", referencedColumnName = "projectName"),
            inverseJoinColumns = @JoinColumn(name = "user_account", referencedColumnName = "account")
    )
    private Set<User> users = new HashSet<User>();

    public Project() {
    }

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
