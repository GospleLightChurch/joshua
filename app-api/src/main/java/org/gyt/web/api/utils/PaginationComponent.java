package org.gyt.web.api.utils;

import org.gyt.web.api.model.PaginationModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationComponent {

    private static final int MAX_PAGE_ITEM = 7;

    public <T> List<T> listPagination(List<T> list, int pageNumber, int pageSize) {
        if (pageNumber > 0 && pageSize > 0) {
            int firstItem = (pageNumber - 1) * pageSize;
            int lastItem = firstItem + pageSize;

            if (firstItem > list.size()) {
                return new ArrayList<>();
            }

            if (lastItem > list.size()) {
                lastItem = list.size();
            }

            return list.subList(firstItem, lastItem);
        }

        return new ArrayList<>();
    }

    public void addPaginationModel(ModelAndView modelAndView, String url, long count, int pageNumber, int pageSize) {
        PaginationModel paginationModel = new PaginationModel();

        if (pageNumber > 0 && pageSize > 0) {
            int totalPage = (int) Math.ceil((double) count / pageSize);

            int middlePointOfPageItemLength = MAX_PAGE_ITEM / 2 + 1;

            if (totalPage <= MAX_PAGE_ITEM) {
                for (int i = 0; i < totalPage; i++) {
                    paginationModel.addItem(i + 1);
                }
            } else {
                if (pageNumber < middlePointOfPageItemLength) {
                    for (int i = 0; i < MAX_PAGE_ITEM; i++) {
                        paginationModel.addItem(i + 1);
                    }
                } else if (pageNumber > totalPage - middlePointOfPageItemLength) {
                    for (int i = MAX_PAGE_ITEM; i > 0; i--) {
                        paginationModel.addItem(totalPage - i + 1);
                    }
                } else {
                    for (int i = 0; i < MAX_PAGE_ITEM; i++) {
                        paginationModel.addItem(pageNumber - middlePointOfPageItemLength + i + 1);
                    }
                }
            }


            if (pageNumber > 1) {
                paginationModel.setPreviousPage(pageNumber - 1);
            } else {
                paginationModel.setPreviousPage(1);
            }

            if (pageNumber < totalPage) {
                paginationModel.setNextPage(pageNumber + 1);
            } else {
                paginationModel.setNextPage(totalPage);
            }

            paginationModel.setFirstPage(1);
            paginationModel.setLastPage(totalPage);
            paginationModel.setPageSize(pageSize);
            paginationModel.setCurrentPage(pageNumber);
            paginationModel.setUrl(url);
        }

        modelAndView.addObject("pagination", paginationModel);
    }
}
