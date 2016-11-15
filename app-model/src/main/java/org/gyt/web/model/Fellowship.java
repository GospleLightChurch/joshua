package org.gyt.web.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 团契实体
 * Created by Administrator on 2016/9/12.
 */
@Entity
@Table
public class Fellowship {

    @Id
    private String name;

    private boolean enable = true;

    private String displayName;

    private Date createdDate;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<User> admins = new ArrayList<>();

    @OneToMany(mappedBy = "fellowship")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "fellowship")
    private List<Message> messages = new ArrayList<>();

    public Fellowship() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
