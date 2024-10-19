package com.itranlin.hexagon.document.api.model;

import java.util.List;

/**
 * Ext doc param.
 */
@SuppressWarnings("unused")
public class ExtDocParam extends ExtDocResult {

    /**
     * Index.
     */
    int index;

    /**
     * Instantiates a new Ext doc param.
     */
    public ExtDocParam() {
    }

    /**
     * Instantiates a new Ext doc param.
     *
     * @param index index
     */
    public ExtDocParam(int index) {
        this.index = index;
    }

    /**
     * Instantiates a new Ext doc param.
     *
     * @param type         type
     * @param desc         desc
     * @param propertyList property list
     * @param index        index
     */
    public ExtDocParam(String type, String desc, List<ExtDocProperty> propertyList, int index) {
        super(type, desc, propertyList);
        this.index = index;
    }

    /**
     * 获取 index.
     *
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * 设置 index.
     *
     * @param index index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ExtDocParam{" +
                "index=" + index +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", propertyList=" + propertyList +
                '}';
    }
}
