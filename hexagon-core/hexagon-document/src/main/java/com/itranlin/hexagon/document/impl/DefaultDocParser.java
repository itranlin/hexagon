package com.itranlin.hexagon.document.impl;

import com.itranlin.hexagon.document.api.HexagonExtApiInterface;
import com.itranlin.hexagon.document.api.HexagonExtApiMethod;
import com.itranlin.hexagon.document.api.HexagonExtApiModel;
import com.itranlin.hexagon.document.api.HexagonExtApiModelProperty;
import com.itranlin.hexagon.document.api.model.*;
import com.itranlin.hexagon.exception.HexagonDocumentException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Default doc parser.
 */
@SuppressWarnings("unchecks")
public class DefaultDocParser implements DocParser {

    /**
     * 获取 all fields.
     *
     * @param clazz clazz
     * @return all fields
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }

        return fields;
    }

    /**
     * 获取 all methods.
     *
     * @param clazz clazz
     * @return all methods
     */
    public static List<Method> getAllMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();

        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            methods.addAll(Arrays.asList(c.getDeclaredMethods()));
        }

        if (clazz != null) {
            for (Class<?> face : clazz.getInterfaces()) {
                methods.addAll(getAllMethods(face));
            }
        }

        return methods;
    }

    @Override
    public ExtDocInterface parse(Class<?> clazz) {

        String name = clazz.getName();
        HexagonExtApiInterface annotation = clazz.getAnnotation(HexagonExtApiInterface.class);
        if (annotation == null) {
            throw new HexagonDocumentException("扩展点接口必须使用 ExtApiInterface 注解");
        }
        String time = annotation.createTime();
        String desc = annotation.description();
        ExtDocInterface result = new ExtDocInterface(name, desc, time);

        List<ExtDocMethod> extDocMethodList = new ArrayList<>();

        List<Method> methods = getAllMethods(clazz);
        for (Method method : methods) {
            String methodName = method.getName();
            HexagonExtApiMethod hexagonExtApiMethod = method.getAnnotation(HexagonExtApiMethod.class);
            if (hexagonExtApiMethod == null) {
                continue;
            }
            String methodTime = hexagonExtApiMethod.createTime();
            String methodDesc = hexagonExtApiMethod.description();
            ExtDocMethod extDocMethod = new ExtDocMethod(methodName, methodDesc, methodTime);

            Class<?> returnType = method.getReturnType();
            ExtDocResult dc = parseModel(returnType);
            extDocMethod.setResult(dc);


            List<ExtDocParam> params = new ArrayList<>();
            for (int i = 0; i < method.getParameters().length; i++) {
                if (method.getParameters()[i].getType().getAnnotation(HexagonExtApiModel.class) == null) {
                    throw new HexagonDocumentException(clazz.getName() + "#" + method.getName() + "#" +
                            method.getParameters()[i].getName() + "参数必须使用 HexagonExtApiModel 注解");
                }
                ExtDocResult res = parseModel(method.getParameters()[i].getType());
                params.add(new ExtDocParam(res.getType(), res.getDesc(), res.getPropertyList(), i));
            }

            extDocMethod.setParams(params);
            extDocMethodList.add(extDocMethod);
        }

        result.setMethods(extDocMethodList);
        return result;
    }

    /**
     * Parse model ext doc result.
     *
     * @param a a
     * @return ext doc result
     */
    public ExtDocResult parseModel(Class<?> a) {

        ExtDocResult result = new ExtDocResult();

        HexagonExtApiModel annotation = a.getAnnotation(HexagonExtApiModel.class);
        if (annotation == null) {
            throw new HexagonDocumentException(a.getName() + " 没有添加注解");
        }
        String name = a.getName();
        result.setType(name);
        String desc = annotation.description();
        result.setDesc(desc);
        result.setPropertyList(new ArrayList<>());

        List<Field> declaredFields = getAllFields(a);
        for (Field field : declaredFields) {
            fieldParse(a, field, result);
        }

        return result;
    }

    private void fieldParse(Class<?> a, Field field, ExtDocResult result) {
        HexagonExtApiModelProperty property = field.getAnnotation(HexagonExtApiModelProperty.class);
        if (property == null) {
            throw new HexagonDocumentException(a.getName() + " 属性 " + field.getName() + " 没有添加注解");
        }
        String typeName = field.getType().getName();
        String desc = property.description();
        ExtDocProperty edp = new ExtDocProperty(typeName, field.getName(), desc);

        result.getPropertyList().add(edp);

        HexagonExtApiModel hexagonExtApiModel = field.getType().getAnnotation(HexagonExtApiModel.class);
        if (hexagonExtApiModel != null) {
            ExtDocResult parse = parseModel(field.getType());
            edp.setPropertyList(parse.getPropertyList());
        }
    }
}
