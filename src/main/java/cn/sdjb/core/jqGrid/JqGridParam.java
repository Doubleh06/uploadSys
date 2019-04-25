package cn.sdjb.core.jqGrid;

/**
 * jqGrid查询参数
 *
 * @author fonlin
 * @date 2018/4/24
 */
public class JqGridParam {

    /**
     * 页数
     */
    private Integer page;


    /**
     * 每页行数
     */
    private Integer rows;

    /**
     * 排序关键词
     */
    private String sidx;

    /**
     * 排序方向
     */
    private String sord;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }
}
