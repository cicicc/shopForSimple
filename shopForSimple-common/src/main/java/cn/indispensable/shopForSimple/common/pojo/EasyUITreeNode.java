package cn.indispensable.shopForSimple.common.pojo;

import java.io.Serializable;

/**
 * 创建一个 pojo 来描述 tree 的节点信息，包含三个属性 id、text、state。
 */
public class EasyUITreeNode implements Serializable {
    private Long id;
    private String text;
    private String state;

    public EasyUITreeNode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
