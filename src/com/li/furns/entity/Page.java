package com.li.furns.entity;

import java.util.List;

/**
 * Page是一个Javabean，是一个分页的数据模型（包含分页的各种信息）
 *
 * @author 李
 * @version 1.0
 */
public class Page<T> {//T表示泛型，将来分页的模型对应的数据是不确定的

    //因为每页显示多少条记录，是其他地方也可能使用的，将其置为static属性
    //选中字符+ctrl+shift+u=>切换大小写
    public static final Integer PAGE_SIZE = 3;

    //当前页
    private Integer pageNo;
    //每页显示的记录数
    private Integer pageSize = PAGE_SIZE;
    //表示一共有多少页，计算出来的
    private Integer pageTotalCount;
    //数据库对应的表一共有多少行记录，通过totalRow和pageSize计算出PageTotalCount
    private Integer totalRow;
    //该页要显示的数据集合
    private List<T> items;
    //分页导航的字符串
    private String url;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
