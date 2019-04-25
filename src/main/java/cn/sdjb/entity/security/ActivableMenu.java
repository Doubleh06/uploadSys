package cn.sdjb.entity.security;

import java.util.List;

/**
 * @author fonlin
 * @date 2018/5/10
 */
public class ActivableMenu {

    private Integer id;

    private String url;

    private String name;

    private String icon;

    private boolean active;

    private String code;

    private boolean displayed;

    private List<ActivableMenu> children;

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


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ActivableMenu> getChildren() {
        return children;
    }

    public void setChildren(List<ActivableMenu> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}
