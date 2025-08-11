package com.system.common.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.system.common.annotation.EnumFiledConvert;
import com.system.common.enums.SymbolConstant;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lutong
 * @data 2025-1-14 014 15:33
 */
public class EasyExcelConvert implements Converter<Object> {

    /**
     * 枚举列表
     */
    private Map<String, String> enumMap = new HashMap<>();
    /**
     * 条件列表
     */
    private Map<String, String> fontColorMap = new HashMap<>();
    /**
     * excel转化后的类型
     *
     * @return
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    /**
     * excel中的数据类型,统一设置字符串
     *
     * @return
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 导入转换
     *
     * @param cellData            当前单元格对象
     * @param contentProperty     当前单元格属性
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String cellMsg = cellData.getStringValue();
        Field field = contentProperty.getField();
        EnumFiledConvert enumFiledConvert = field.getAnnotation(EnumFiledConvert.class);
        if (enumFiledConvert == null) {
            return null;
        }
        String enumStr = enumFiledConvert.enumMap();
        // 解析枚举映射关系
        getEnumMap(enumStr, true);
        // 是否为单选
        boolean single = enumFiledConvert.single();
        // 如果是单选，默认Java属性为integer
        if (single) {
            String res = enumMap.get(cellMsg);
            return StringUtils.hasText(res) ? Integer.valueOf(res) : null;
        } else {
            // 多选分隔符
            String spiteChar = enumFiledConvert.spiteChar();
            // 多选枚举，默认Java属性为字符串，格式为 key1,key2,key3
            List<String> strStr = Arrays.asList(cellMsg.split(spiteChar)).stream().map(s -> String.valueOf(enumMap.get(s))).collect(Collectors.toList());
            String str = String.join(spiteChar, strStr);
            return str;
        }
    }

    /**
     * 导出转化
     *
     * @param value               当前值
     * @param contentProperty     当前单元格属性
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Field field = contentProperty.getField();
        EnumFiledConvert enumFiledConvert = field.getAnnotation(EnumFiledConvert.class);
        if (enumFiledConvert == null) {
            return new WriteCellData();
        }
        // 解析枚举字符串
        String enumStr = enumFiledConvert.enumMap();
        getEnumMap(enumStr, false);
        // 是否为单选
        boolean single = enumFiledConvert.single();

        //条件样式
        String conditions = enumFiledConvert.fontColorMap();
        //解析条件样式
        getConditionsMap(conditions);


        // 如果是单选，默认Java属性为integer
        if (single) {
            //输出内容原始值
            String valueOf = String.valueOf(value);

            //输出内容转换，字典值
            String orDefault = enumMap.getOrDefault(valueOf, "");
            //创建输出内容
            WriteCellData writeCellData = new WriteCellData(orDefault);
            setWriteCellStyle(writeCellData, orDefault);

            return writeCellData;
        } else {
            // 多选分隔符
            String spiteChar = enumFiledConvert.spiteChar();
            // 多选枚举，默认Java属性为字符串，格式为 key1,key2,key3
            List<String> strStr = Arrays.asList(String.valueOf(value).split(spiteChar)).stream().map(s -> String.valueOf(enumMap.get(s))).collect(Collectors.toList());
            String str = String.join(spiteChar, strStr);
            WriteCellData writeCellData = new WriteCellData(str);

            return writeCellData;
        }
    }

    /**
     * 根据注解配置的枚举映射字符串进行解析到map中
     *
     * @param mapStr
     * @param readOrWrite 读excel 、 写excel
     */
    private void getEnumMap(String mapStr, boolean readOrWrite) {
        String[] enumS = mapStr.split(SymbolConstant.SPE1);
        for (String anEnum : enumS) {
            String[] data = anEnum.split(SymbolConstant.SPE9);
            if (readOrWrite) {
                // 读excel excel中的数据都是value，转换成key
                enumMap.put(data[1], data[0]);
            } else {
                // 写excel  Java中的数据都是key，转换成value
                enumMap.put(data[0], data[1]);
            }
        }
    }

    /**
     * 根据注解配置的枚举映射字符串进行解析到map中
     * 只有写出的时候生效
     */
    private void getConditionsMap(String conditions) {
        if (conditions != null && !conditions.isEmpty()) {
            String[] conditionsMaps = conditions.split(SymbolConstant.SPE1);
            for (String condition : conditionsMaps) {
                String[] data = condition.split(SymbolConstant.SPE9);
                // 写excel  Java中的数据都是key，转换成value
                fontColorMap.put(data[0], data[1]);
            }
        }
    }


    /**
     *
     * 取到什么颜色，填充什么颜色，没有则不填充。
     *
     * @param writeCellData
     */
    private void setWriteCellStyle(WriteCellData writeCellData, String orDefault) {
        //输出样式转换，颜色值，默认黑色
        short orDefaultStyle = 0;

        //条件，满足条件则修改内容，可以叠加更多的条件，只要符合k-v规则，即比较k,填充v
        //因为这里是中文k 和 颜色索引，这里比较的就是中文，如果能保障k不重复，则k可以是数字、字符等，对应的v也就可以是数字、字符等，
        //这样就可以按照需求对单元格式进行一定的操作编辑
        //需要注意的是，这里用的转换之后的输出值进行的比较，有需要使用原始值的可以自行微调一下
        if (fontColorMap != null && fontColorMap.containsKey(orDefault)) {
            WriteFont writeFont = new WriteFont();
            WriteCellStyle writeCellStyle = new WriteCellStyle();
            orDefaultStyle = Short.parseShort(fontColorMap.getOrDefault(orDefault, ""));
            writeFont.setColor(orDefaultStyle);
            writeCellStyle.setWriteFont(writeFont);
            writeCellData.setWriteCellStyle(writeCellStyle);
        }
    }
}
