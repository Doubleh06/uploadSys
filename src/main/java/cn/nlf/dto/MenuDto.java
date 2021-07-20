package cn.nlf.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author fonlin
 * @date 2018/6/21
 */
public class MenuDto {

    private Integer id;

    //url
    @NotEmpty(message = "url不能是空")
    private String url;

    //名称
    @NotEmpty(message = "名称不能是空")
    private String name;

    //排序
    private Integer sequence;

    //key，唯一
    @NotEmpty(message = "code不能是空")
    private String code;

    //图标
    private String icon;

    //描述
    private String description;

    //上级菜单code
    private String pcode;

    //类型 1-菜单 0-不是菜单
    private Integer type;

    //是否启用
    private Boolean enabled;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
