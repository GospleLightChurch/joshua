package org.gyt.web.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页模型
 * Created by y27chen on 2016/9/29.
 */
public class PaginationModel {

    private String url;

    private int pageSize;

    private int currentPage;

    private int firstPage;

    private int lastPage;

    private int previousPage;

    private int nextPage;

    private List<Integer> items = new ArrayList<>();

    public PaginationModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void addItem(Integer item) {
        this.items.add(item);
    }
}
