package com.itranlin.hexagon.document.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Ext doc property.
 */
@SuppressWarnings("unused")
public class ExtDocProperty {

    /**
     * Type.
     */
    String type;

    /**
     * Name.
     */
    String name;

    /**
     * Desc.
     */
    String desc;

    /**
     * Generic type list.
     */
    List<ExtDocProperty> genericTypeList = new ArrayList<>();

    /**
     * Property list.
     */
    List<ExtDocProperty> propertyList;

    /**
     * Instantiates a new Ext doc property.
     */
    public ExtDocProperty() {
    }

    /**
     * Instantiates a new Ext doc property.
     *
     * @param type type
     * @param name name
     * @param desc desc
     */
    public ExtDocProperty(String type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    /**
     * Instantiates a new Ext doc property.
     *
     * @param type            type
     * @param name            name
     * @param desc            desc
     * @param genericTypeList generic type list
     * @param propertyList    property list
     */
    public ExtDocProperty(String type, String name, String desc, List<ExtDocProperty> genericTypeList, List<ExtDocProperty> propertyList) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.genericTypeList = genericTypeList;
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
     * 获取 name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 name.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取 generic type list.
     *
     * @return generic type list
     */
    public List<ExtDocProperty> getGenericTypeList() {
        return genericTypeList;
    }

    /**
     * 设置 generic type list.
     *
     * @param genericTypeList generic type list
     */
    public void setGenericTypeList(List<ExtDocProperty> genericTypeList) {
        this.genericTypeList = genericTypeList;
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
        if (!(o instanceof ExtDocProperty that)) return false;

        return Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects.equals(desc, that.desc) && Objects.equals(genericTypeList, that.genericTypeList) && Objects.equals(propertyList, that.propertyList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(type);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(desc);
        result = 31 * result + Objects.hashCode(genericTypeList);
        result = 31 * result + Objects.hashCode(propertyList);
        return result;
    }

    @Override
    public String toString() {
        return "ExtDocProperty{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", genericTypeList=" + genericTypeList +
                ", propertyList=" + propertyList +
                '}';
    }
}
