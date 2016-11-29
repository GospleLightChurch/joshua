package org.gyt.web.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 图片实体
 * Created by y27chen on 2016/11/29.
 */
@Entity
@Table
public class Image {

    @Id
    @GenericGenerator(name = "idGen", strategy = "uuid2")
    @GeneratedValue(generator = "idGen")
    private String id;

    private String link;

    private String url;

    @Lob
    private byte[] content;

    @ManyToOne
    private User owner;

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
