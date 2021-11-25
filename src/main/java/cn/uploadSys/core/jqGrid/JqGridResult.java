package cn.uploadSys.core.jqGrid;

import java.util.List;
import java.util.Map;

/**
 * 页面表格结果集映射Bean
 */
public class JqGridResult<T> {
    /**
     * 页码
     */
    private int page;
    /**
     * 数据总数
     */
    private long total;
    /**
     * 结果总数
     */
    private long records;
    /**
     * 结果集
     */
    private List<T> rows;
    /**
     * 用户自定义数据
     */
    private Map userdata;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Map getUserdata() {
        return userdata;
    }

    public void setUserdata(Map userdata) {
        this.userdata = userdata;
    }

}
