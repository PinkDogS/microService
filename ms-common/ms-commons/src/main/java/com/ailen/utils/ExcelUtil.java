package com.ailen.utils;//package com.ailen.utils;
//
//
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.util.List;
//
///**
// * @author anshare
// * @Package com.anshare.base.anyfix.utils
// *
// * @note
// */
//public class ExcelUtil {
//
//    /**
//     * 导出 Excel ：一个 sheet，带表头.
//     *
//     * @param response  HttpServletResponse
//     * @param data      数据 list，每个元素为一个 BaseRowModel
//     * @param fileName  导出的文件名
//     * @param sheetName 导入文件的 sheet 名
//     * @param model     映射实体类，Excel 模型
//     * @throws Exception 异常
//     */
//    public static void writeExcel(HttpServletResponse response, List<? extends Object> data,
//                                  String fileName, String sheetName, Class model,
//                                  SheetWriteHandler sheetWriteHandler) throws Exception {
//
//        // 头的策略
//        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
//        //设置表头居中对齐
//        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        // 内容的策略
//        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
//
//        //设置内容靠左对齐
//        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
//        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
//        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
//                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        EasyExcel.write(getOutputStream(fileName, response), model)
//                .excelType(ExcelTypeEnum.XLSX)
//                .sheet(sheetName)
//                .registerWriteHandler(horizontalCellStyleStrategy)
//                .registerWriteHandler(sheetWriteHandler)
//                //最大长度自适应 目前没有对应算法优化 建议注释掉不用 会出bug
////                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                .doWrite(data);
//    }
//
//    /**
//     * 导出 Excel ：一个 sheet，带表头. 不包含样式
//     *
//     * @param response                HttpServletResponse
//     * @param data                    数据 list，每个元素为一个 BaseRowModel
//     * @param fileName                导出的文件名
//     * @param sheetName               导入文件的 sheet 名
//     * @param model                   映射实体类，Excel 模型
//     * @param sheetWriteHandler       sheet处理类
//     * @param excludeColumnFiledNames 忽略的列
//     * @throws Exception 异常
//     */
//    public static void writeExcel(HttpServletResponse response, List<? extends Object> data,
//                                  String fileName, String sheetName, Class model,
//                                  SheetWriteHandler sheetWriteHandler,
//                                  List<String> excludeColumnFiledNames) throws Exception {
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        EasyExcel.write(getOutputStream(fileName, response), model)
//                .excludeColumnFiledNames(excludeColumnFiledNames)
//                .excelType(ExcelTypeEnum.XLSX)
//                .sheet(sheetName)
//                .registerWriteHandler(sheetWriteHandler)
//                //最大长度自适应 目前没有对应算法优化 建议注释掉不用 会出bug
////                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                .doWrite(data);
//    }
//
//    /**
//     * 导出文件时为Writer生成OutputStream.
//     *
//     * @param fileName 文件名
//     * @param response response
//     * @return ""
//     */
//    private static OutputStream getOutputStream(String fileName,
//                                                HttpServletResponse response) throws Exception {
//        try {
//            fileName = URLEncoder.encode(fileName, "UTF-8");
//            response.setContentType("application/vnd.ms-excel");
//            response.setCharacterEncoding("UTF-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
//            response.setHeader("Pragma", "public");
//            response.setHeader("Cache-Control", "no-store");
//            response.addHeader("Cache-Control", "max-age=0");
//            return response.getOutputStream();
//        } catch (Exception e) {
//            throw new Exception("导出excel表格失败!", e);
//        }
//    }
//}
