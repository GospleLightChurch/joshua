package org.gyt.web.core.model;

/**
 * Nokia ChengDu Engine Team
 * Created by y27chen on 2016/7/9.
 */
public class PaginationItem {

    private String title;

    private String link;

    private boolean isActive;

    public PaginationItem() {
    }

    public PaginationItem(String title, String link, boolean isActive) {
        this.title = title;
        this.link = link;
        this.isActive = isActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
