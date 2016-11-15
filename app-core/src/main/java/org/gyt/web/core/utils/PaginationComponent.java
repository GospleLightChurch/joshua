package org.gyt.web.core.utils;

import org.gyt.web.core.model.PaginationItem;
import org.gyt.web.core.model.PaginationModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationComponent {

    private static final int DEFAULT_PAGINATION_SIZE = 8;

    private static final String PAGE_PRE = "pagePre";
    private static final String PAGE_NEXT = "pageNext";
    private static final String PAGE_FIRST = "pageFirst";
    private static final String PAGE_LAST = "pageLast";
    private static final String PAGE_ITEM = "pageItem";
    private static final String PAGE_FORMAT = "%s?page=%d&size=%d";

    public void addPagination(ModelAndView modelAndView, Page page, String baseUrl) {
        int currentPage = page.getNumber();
        int pageSize = page.getSize();
        int totalPage = page.getTotalPages();

        modelAndView.addObject(PAGE_PRE, String.format(PAGE_FORMAT, baseUrl, page.isFirst() ? 0 : page.previousPageable().getPageNumber(), pageSize));
        modelAndView.addObject(PAGE_NEXT, String.format(PAGE_FORMAT, baseUrl, page.isLast() ? totalPage - 1 : page.nextPageable().getPageNumber(), pageSize));

        modelAndView.addObject(PAGE_FIRST, String.format(PAGE_FORMAT, baseUrl, 0, pageSize));
        modelAndView.addObject(PAGE_LAST, String.format(PAGE_FORMAT, baseUrl, totalPage - 1, pageSize));

        int baseNumber = currentPage / DEFAULT_PAGINATION_SIZE;

        List<PaginationItem> paginationItems = new ArrayList<>();

        for (int i = 0; i < DEFAULT_PAGINATION_SIZE; i++) {
            int target = baseNumber * DEFAULT_PAGINATION_SIZE + i + 1;

            if (target > totalPage) {
                continue;
            }

            PaginationItem paginationItem = new PaginationItem();
            paginationItem.setTitle(String.valueOf(target));
            paginationItem.setLink(String.format(PAGE_FORMAT, baseUrl, target - 1, pageSize));

            if (currentPage == target - 1) {
                paginationItem.setActive(true);
            }

            paginationItems.add(paginationItem);
        }

        modelAndView.addObject(PAGE_ITEM, paginationItems);
    }
}
