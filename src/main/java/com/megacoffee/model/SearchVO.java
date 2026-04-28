package com.megacoffee.model;

public class SearchVO {
    private String searchType;
    private String searchValue;
    private String startDate;
    private String endDate;

    // 페이징 관련 필드
    private int page = 1;
    private int size = 10;
    private int offset = 0;
    private int limit = 10;

    public String getSearchType() {
        return searchType;
    }
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    public String getSearchValue() {
        return searchValue;
    }
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
        this.offset = (page - 1) * this.size;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
        this.limit = size;
        this.offset = (this.page - 1) * size;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
}