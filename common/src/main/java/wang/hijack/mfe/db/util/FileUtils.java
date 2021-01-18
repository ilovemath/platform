package wang.hijack.mfe.db.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.var;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jack
 */
public class FileUtils {
    public static void downloadExcel(List<Map<String, Object>> list, HttpServletResponse response) {
        ExcelWriter writer = ExcelUtil.getBigWriter();
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        try (var out = response.getOutputStream()) {
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Serializable> void download(List<T> objects, HttpServletResponse response) {
        String format = "yyyy年MM月dd日 HH:mm:ss";
        List<Map<String, Object>> list = new ArrayList<>();
        for (var obj : objects) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (var field : obj.getClass().getDeclaredFields()) {
                try {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = field.get(obj);
                    if (name.contains("Time")) {
                        map.put(name, DateUtil.format(new Date((Long) value), format));
                    } else {
                        map.put(name, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            list.add(map);
        }
        downloadExcel(list, response);
    }
}
