package com.itranlin.hexagon.document.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Ext doc result.
 */
public class ExtDocResult implements Serializable {

    /**
     * Type.
     */
    String type;

    /**
     * Desc.
     */
    String desc;

    /**
     * Property list.
     */
    List<ExtDocProperty> propertyList;

    /**
     * Instantiates a new Ext doc result.
     */
    public ExtDocResult() {
    }

    /**
     * Instantiates a new Ext doc result.
     *
     * @param type         type
     * @param desc         desc
     * @param propertyList property list
     */
    public ExtDocResult(String type, String desc, List<ExtDocProperty> propertyList) {
        this.type = type;
        this.desc = desc;
        this.propertyList = propertyList;
    }

    /**
     * 获取 type.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * 设置 type.
     *
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取 desc.
     *
     * @return desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置 desc.
     *
     * @param desc desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取 property list.
     *
     * @return property list
     */
    public List<ExtDocProperty> getPropertyList() {
        return propertyList;
    }

    /**
     * 设置 property list.
     *
     * @param propertyList property list
     */
    public void setPropertyList(List<ExtDocProperty> propertyList) {
        this.propertyList = propertyList;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExtDocResult that)) return false;

        return Objects.equals(type, that.type) && Objects.equals(desc, that.desc) && Objects.equals(propertyList, that.propertyList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(type);
        result = 31 * result + Objects.hashCode(desc);
        result = 31 * result + Objects.hashCode(propertyList);
        return result;
    }

    @Override
    public String toString() {
        return "ExtDocResult{" +
                "type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", propertyList=" + propertyList +
                '}';
    }
}
