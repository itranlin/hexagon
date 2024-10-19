package com.itranlin.hexagon.document.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Ext doc interface.
 */
@SuppressWarnings("unused")
public class ExtDocInterface implements Serializable {

    /**
     * Interface name.
     */
    String interfaceName;

    /**
     * Desc.
     */
    String desc;

    /**
     * Create time.
     */
    String createTime;

    /**
     * Methods.
     */
    List<ExtDocMethod> methods;

    /**
     * Instantiates a new Ext doc interface.
     */
    public ExtDocInterface() {
    }

    /**
     * Instantiates a new Ext doc interface.
     *
     * @param interfaceName interface name
     * @param desc          desc
     * @param createTime    create time
     */
    public ExtDocInterface(String interfaceName, String desc, String createTime) {
        this.interfaceName = interfaceName;
        this.desc = desc;
        this.createTime = createTime;
    }

    /**
     * Instantiates a new Ext doc interface.
     *
     * @param interfaceName interface name
     * @param desc          desc
     * @param createTime    create time
     * @param methods       methods
     */
    public ExtDocInterface(String interfaceName, String desc, String createTime, List<ExtDocMethod> methods) {
        this.interfaceName = interfaceName;
        this.desc = desc;
        this.createTime = createTime;
        this.methods = methods;
    }

    /**
     * Gets interface name.
     *
     * @return interface name
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * Sets interface name.
     *
     * @param interfaceName interface name
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * Gets desc.
     *
     * @return desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets desc.
     *
     * @param desc desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Gets create time.
     *
     * @return create time
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Sets create time.
     *
     * @param createTime create time
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets methods.
     *
     * @return methods
     */
    public List<ExtDocMethod> getMethods() {
        return methods;
    }

    /**
     * Sets methods.
     *
     * @param methods methods
     */
    public void setMethods(List<ExtDocMethod> methods) {
        this.methods = methods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtDocInterface that = (ExtDocInterface) o;
        return Objects.equals(interfaceName, that.interfaceName) && Objects.equals(desc, that.desc) && Objects.equals(createTime, that.createTime) && Objects.equals(methods, that.methods);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(interfaceName);
        result = 31 * result + Objects.hashCode(desc);
        result = 31 * result + Objects.hashCode(createTime);
        result = 31 * result + Objects.hashCode(methods);
        return result;
    }

    @Override
    public String toString() {
        return "ExtDocInterface{" +
                "interfaceName='" + interfaceName + '\'' +
                ", desc='" + desc + '\'' +
                ", createTime='" + createTime + '\'' +
                ", methods=" + methods +
                '}';
    }
}
