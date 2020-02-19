package com.example.source.item.response;

import lombok.ToString;

import java.util.List;

/**
 * 封装分页数据
 */
@ToString
public class PageDataResult {

    private Integer code=200;

    private List<?> list;
    //总记录数量  内部用
    private Integer totals;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }
}
