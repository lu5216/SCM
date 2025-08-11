package com.system.common.utils;

import com.alibaba.excel.EasyExcel;
import com.system.common.listener.ImportExcelListener;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 *  Excel导入导出工具类
 * @author lutong
 * @data 2024-11-28 028 13:49
 */
public class UploadOrExportExcelUtil<T> {

    /**
     * 导入,需要给相关类添加无参构造
     */
    @ApiModelProperty("导入")
    public List<T> uploadExport(MultipartFile file, Class<T> tClass) throws IOException {
        ImportExcelListener<T> excelListener = new ImportExcelListener<>();
        EasyExcel.read(file.getInputStream(), tClass, excelListener).sheet().doRead();
        return excelListener.getDataList();
    }

    /**
     * 导出
     */
    @ApiModelProperty("导出")
    public void exportExcel(HttpServletResponse response, List<T> data, Class<T> tClass, String name, String suffix) throws IOException {
        if (suffix == null || suffix.equals("")) {
            suffix = "xlsx";
        }
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(name, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + "." + suffix);
        EasyExcel.write(response.getOutputStream(), tClass)
                .sheet(name)
                .doWrite(data);
    }
}
