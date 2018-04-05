package com.base.common;

public class QueryBase {

    /**
     * 要排序的字段名
     */
    protected String sort;

    /**
     * 排序方式: desc \ asc
     */
    protected String order;

    /**
     * 获取一页行数
     */
    protected int limit;

    /**
     * 获取的页码
     */
    protected int page;

    /**
     * 起始记录
     */
    protected int offset;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        if (sort == null) {
            return;
        }
        String orderSort = "";
        for (int i = 0; i < sort.length(); i++) {
            char a = sort.charAt(i);
            String b = "" + a;
            if (a > 64 && a < 91) {
                if (orderSort.equals("")) {
                    orderSort += b.toLowerCase();
                } else {
                    orderSort += "_" + b.toLowerCase();
                }
            } else {
                orderSort += b;
            }
        }
        this.sort = orderSort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return (this.page - 1) * limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
