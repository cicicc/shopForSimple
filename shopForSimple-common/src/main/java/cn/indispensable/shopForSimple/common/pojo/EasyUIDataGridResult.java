package cn.indispensable.shopForSimple.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable {
    private Integer total;
    private List rows;

    public EasyUIDataGridResult() {
    }

    public EasyUIDataGridResult(Integer total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    public void setTotal(Long total) {
        this.total = total.intValue();
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
