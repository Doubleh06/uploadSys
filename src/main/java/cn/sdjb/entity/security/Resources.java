package cn.sdjb.entity.security;

import java.util.List;

/**
 * @author fonlin
 * @date 2018/6/27
 */
public class Resources {

    private String domain;

    private List<Resource> resources;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
