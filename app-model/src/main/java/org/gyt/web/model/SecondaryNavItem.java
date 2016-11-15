package org.gyt.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 二级导航项目实体
 * Created by Administrator on 2016/9/12.
 */
@Entity
@Table
public class SecondaryNavItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String link;

    private Integer position;

    public SecondaryNavItem() {
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
}
