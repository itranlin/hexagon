package com.itranlin.hexagon.document.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Ext doc method.
 */
@SuppressWarnings("unused")
public class ExtDocMethod implements Serializable {

    /**
     * Method name.
     */
    String methodName;

    /**
     * Desc.
     */
    String desc;

    /**
     * Create time.
     */
    String createTime;

    /**
     * Params.
     */
    List<ExtDocParam> params;

    /**
     * Result.
     */
    ExtDocResult result;

    /**
     * Instantiates a new Ext doc method.
     */
    public ExtDocMethod() {
    }

    /**
     * Instantiates a new Ext doc method.
     *
     * @param methodName method name
     * @param desc       desc
     * @param createTime create time
     */
    public ExtDocMethod(String methodName, String desc, String createTime) {
        this.methodName = methodName;
        this.desc = desc;
        this.createTime = createTime;
    }

    /**
     * Instantiates a new Ext doc method.
     *
     * @param methodName method name
     * @param desc       desc
     * @param createTime create time
     * @param params     params
     * @param result     result
     */
    public ExtDocMethod(String methodName, String desc, String createTime, List<ExtDocParam> params, ExtDocResult result) {
        this.methodName = methodName;
        this.desc = desc;
        this.createTime = createTime;
        this.params = params;
        this.result = result;
    }

    /**
     * 获取 method name.
     *
     * @return method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置 method name.
     *
     * @param methodName method name
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
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
     * 获取 create time.
     *
     * @return create time
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置 create time.
     *
     * @param createTime create time
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 params.
     *
     * @return params
     */
    public List<ExtDocParam> getParams() {
        return params;
    }

    /**
     * 设置 params.
     *
     * @param params params
     */
    public void setParams(List<ExtDocParam> params) {
        this.params = params;
    }

    /**
     * 获取 result.
     *
     * @return result
     */
    public ExtDocResult getResult() {
        return result;
    }

    /**
     * 设置 result.
     *
     * @param result result
     */
    public void setResult(ExtDocResult result) {
        this.result = result;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExtDocMethod that)) return false;

        return Objects.equals(methodName, that.methodName) && Objects.equals(desc, that.desc) && Objects.equals(createTime, that.createTime) && Objects.equals(params, that.params) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        int result1 = Objects.hashCode(methodName);
        result1 = 31 * result1 + Objects.hashCode(desc);
        result1 = 31 * result1 + Objects.hashCode(createTime);
        result1 = 31 * result1 + Objects.hashCode(params);
        result1 = 31 * result1 + Objects.hashCode(result);
        return result1;
    }

    @Override
    public String toString() {
        return "ExtDocMethod{" +
                "methodName='" + methodName + '\'' +
                ", desc='" + desc + '\'' +
                ", createTime='" + createTime + '\'' +
                ", params=" + params +
                ", result=" + result +
                '}';
    }
}
