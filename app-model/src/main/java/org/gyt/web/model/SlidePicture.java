package org.gyt.web.model;

import javax.persistence.*;

/**
 * 首页活动大图
 * Created by y27chen on 2016/10/21.
 */
@Entity
@Table
public class SlidePicture {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] content;

    private String link;

    public SlidePicture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
