package com.system.systembase.api.basicConfiguration.converter;

import com.system.systembase.api.basicConfiguration.converter.annotation.DictConvert;
import com.system.systembase.api.basicConfiguration.converter.vo.DictFieldRelation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author ZuiYing
 * @name BaseDictConvert
 * @data 2025-08-06
 */

@Slf4j
public class BaseDictConvert{

//    static {
//        // 初始化模拟字典数据
//        Map<String, String> nationDict = new HashMap<>();
//        nationDict.put("CN", "中国");
//        nationDict.put("US", "美国");
//        nationDict.put("JP", "日本");
//        DICT_CACHE.put("NATION", nationDict);
//
//        Map<String, String> goodsTypeDict = new HashMap<>();
//        goodsTypeDict.put("MATERIAL", "原材料");
//        goodsTypeDict.put("FINISHED", "成品");
//        goodsTypeDict.put("CONSUMABLE", "易耗品");
//        DICT_CACHE.put("GOODS_TYPE", goodsTypeDict);
//    }

    public static <T> void convertDict(T entity) {
        if (entity == null) return;

        Class<?> clazz = entity.getClass();

        // 遍历实体类的所有字段，检查字段上是否有 @DictConvert 注解
        Map<String, DictFieldRelation> relations = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            DictConvert annotation = field.getAnnotation(DictConvert.class);
            if (annotation != null) {
                relations.put(field.getName(), new DictFieldRelation(annotation, field.getName()));
            }
        }

        // 执行实际转换
        Field[] fields = clippedDeclaredFields(clazz);
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                DictConvert annotation = field.getAnnotation(DictConvert.class);
                if (annotation != null) {
                    // 根据注解配置获取关联字段
                    DictFieldRelation relation = relations.get(field.getName());

                    // 获取待转换的字段
                    Field sourceField = findFieldByName(fields, relation.getSourceField());

                    // 执行双向转换
                    performConversion(entity, field, sourceField.get(entity), relation);
                } else {
                    // 检查是否为关联字段
                    for (DictFieldRelation relation : relations.values()) {
                        if (Objects.equals(field.getName(), relation.getTargetField())) {
                            // 获取待转换的字段
                            Field sourceField = findFieldByName(fields, relation.getSourceField());

                            performConversion(entity, field, sourceField.get(entity), relation);
                        }
                    }
                }
            } catch (Exception e) {
                log.info("字典转换失败: " + field.getName());
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取所有字段（包含父类）
     * @param clazz
     * @return
     */
    private static Field[] clippedDeclaredFields(Class<?> clazz) {
        // 使用两个列表分别存储
        List<Field> nonAnnotatedFields = new ArrayList<>();
        List<Field> annotatedFields = new ArrayList<>();

        Class<?> current = clazz;
        while (current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                // 检查是否有DictConvert注解
                if (field.isAnnotationPresent(DictConvert.class)) {
                    annotatedFields.add(field);
                } else {
                    nonAnnotatedFields.add(field);
                }
            }
            current = current.getSuperclass();
        }

        // 合并列表：先非注解字段，后注解字段
        List<Field> allFields = new ArrayList<>();
        allFields.addAll(nonAnnotatedFields);
        allFields.addAll(annotatedFields);
        return allFields.toArray(new Field[0]);
    }


    /**
     * 执行字典转换
     * @param entity
     * @param currentField
     * @param currentValue
     * @param relation
     * @param <T>
     * @throws Exception
     */
    private static <T> void performConversion(T entity, Field currentField, Object currentValue,
                                              DictFieldRelation relation) throws Exception {
        String parentCode = relation.getParentCode();
        String fieldValue = String.valueOf(currentValue);

        // 从缓存获取字典映射
        Map<String, String> dictMap = DictCacheManager.getDictMap(parentCode);

        if (dictMap == null || dictMap.isEmpty()) {
            log.warn("字典数据未加载: {}", parentCode);
            return;
        }

        // 执行转换逻辑
        if (!currentField.getName().equals(relation.getTargetField())) {
            // 刷新字典缓存
            log.info("刷新字典缓存...");
            dictMap = DictCacheManager.getDictMap(parentCode);
        }

        // 根据code找name
        String valueName = dictMap.get(fieldValue);
        // 根据name找code
        String valueCode = dictMap.entrySet().stream()
                .filter(e -> e.getValue().equals(fieldValue))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (valueName != null) {
            setFieldValue(entity, relation.getTargetField(), valueName);
        }
        if (valueCode != null) {
            setFieldValue(entity, relation.getTargetField(), valueCode);
        }

        // 如果都查询不到对于字段，刷新缓存，再查一遍
        if (valueName == null && valueCode == null){
            // 刷新字典缓存
            log.info("刷新字典缓存...");
            dictMap = DictCacheManager.getDictMap(parentCode);

            // 根据code找name
            valueName = dictMap.get(fieldValue);
            // 根据name找code
            valueCode = dictMap.entrySet().stream()
                    .filter(e -> e.getValue().equals(fieldValue))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            if (valueName != null) {
                setFieldValue(entity, relation.getTargetField(), valueName);
            }
            if (valueCode != null) {
                setFieldValue(entity, relation.getTargetField(), valueCode);
            }
        }
    }


    /**
     * 设置字段值
     * @param entity
     * @param fieldName
     * @param value
     * @param <T>
     * @throws Exception
     */
    private static <T> void setFieldValue(T entity, String fieldName, Object value) throws Exception {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                // 类型转换
                if (field.getType() == Long.class) {
                    field.set(entity, Long.valueOf(value.toString()));
                } else {
                    field.set(entity, value.toString());
                }
                return;
            }
        }
    }


    /**
     * 在字段列表中查找指定名称的字段
     * @param fields 字段列表
     * @param fieldName 要查找的字段名称
     * @return 找到的字段，如果未找到则返回null
     */
    private static Field findFieldByName(Field[] fields, String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
}
