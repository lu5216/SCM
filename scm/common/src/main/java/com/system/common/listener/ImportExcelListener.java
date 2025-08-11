package com.system.common.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 *  Excel导入数据-监听器
 * @author lutong
 * @data 2024-11-28 028 11:51
 * 导入监听器
 */
public class ImportExcelListener<T> extends AnalysisEventListener<T>  {
    private List<T> list = new ArrayList<>();

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 可以在这里进行数据处理，例如保存到数据库
        System.out.println("所有数据解析完成！");
    }

    public List<T> getDataList() {
        return list;
    }

}
