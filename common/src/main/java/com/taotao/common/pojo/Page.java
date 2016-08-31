package com.taotao.common.pojo;

import java.util.List;

/**
 * Created by XDStation on 2016/7/16 0016.
 */
public class Page {
    //分页信息
    private Long total;     //总数据条数
    private List<?> rows;      //数据

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
