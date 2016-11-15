package org.gyt.web.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 一级导航项目实体
 * Created by Administrator on 2016/9/12.
 */
@Entity
@Table
public class PrimaryNavItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String link;

    private Integer position;

    @OneToMany
    private List<SecondaryNavItem> children = new ArrayList<>();

    public PrimaryNavItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<SecondaryNavItem> getChildren() {
        return children;
    }

    public void setChildren(List<SecondaryNavItem> children) {
        this.children = children;
    }
}
