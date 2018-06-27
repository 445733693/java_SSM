package com.how2java.tmall.util;

public class Page {
    private Integer start=0;
    private Integer count;
    private Integer total;
    private String param;

    private static final Integer DEFAULTCOUNT=5;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Page(){
        count=DEFAULTCOUNT;
    }

    public Page(Integer start,Integer count) {
        this.start=start;
        this.count=count;
    }

    public boolean isHasPreviouse() {
        if (start > 0) {
            return true;
        }
        return false;
    }

    public boolean isHasNext() {
        if(start==getLast()){
            return false;
        }
        return true;
    }

    public Integer getTotalPage() {
        Integer totalPage;
        totalPage=total/count;
        if(totalPage==0) return 1;
        return total%count==0?totalPage:totalPage+1;
    }

    public Integer getLast(){
        Integer last;
        if (total % count == 0) {
            last = total - count;
        }else {
            last=total-total%count;
        }
        last=last<0?0:last;
        return last;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + isHasPreviouse() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }
}
