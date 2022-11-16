package ar.edu.itba.paw.models;

import java.util.List;

public class Pagination<T>{
    private final List<T> items;
    private final long page;
    private final long pageCount;

    public Pagination(List<T> items, long page, long pageCount) {
        this.items = items;
        this.page = page;
        this.pageCount = pageCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public long getPage() {
        return page;
    }

    public List<T> getItems() {
        return items;
    }
}
