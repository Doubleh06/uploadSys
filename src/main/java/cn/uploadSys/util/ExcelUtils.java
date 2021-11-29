package cn.uploadSys.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import cn.hutool.poi.excel.sax.handler.RowHandler;
import cn.uploadSys.core.BusinessException;
import cn.uploadSys.core.ErrorCode;
import com.alibaba.fastjson.JSONArray;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author dh
 * @version 1.0
 * @description: TODO
 * @date 2021/11/26 下午1:27
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private static List<List<Object>> lineList = new ArrayList<>();

    /**
     * excel 导出工具类
     *
     * @param response
     * @param fileName    文件名
     * @param projects    对象集合
     * @param columnNames 导出的excel中的列名
     * @param keys        对应的是对象中的字段名字
     * @throws IOException
     */
    public static void export(HttpServletResponse response, String fileName, List<?> projects, String[] columnNames, String[] keys) throws IOException {

        ExcelWriter bigWriter = ExcelUtil.getBigWriter();

        for (int i = 0; i < columnNames.length; i++) {
            bigWriter.addHeaderAlias(columnNames[i], keys[i]);
            bigWriter.setColumnWidth(i, 20);
        }
        // 一次性写出内容，使用默认样式，强制输出标题
        bigWriter.write(projects, true);
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        bigWriter.flush(out, true);
        // 关闭writer，释放内存
        bigWriter.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
     * excel导入工具类
     *
     * @param file       文件
     * @param columNames 列对应的字段名
     * @return 返回数据集合
     * @throws IOException
     */
    public static List<Map<String, Object>> leading(MultipartFile file, Object[] columNames) throws BusinessException, IOException {
        String fileName = file.getOriginalFilename();
        // 上传文件为空
        if (StringUtils.isEmpty(fileName)) {
            throw new BusinessException(ErrorCode.OPERATION_EXCEL_ERROR, "没有导入文件");
        }
        //上传文件大小为1000条数据
        if (file.getSize() > 1024 * 1024 * 10) {
            logger.error("upload | 上传失败: 文件大小超过10M，文件大小为：{}", file.getSize());
            throw new BusinessException(ErrorCode.OPERATION_EXCEL_ERROR, "上传失败: 文件大小不能超过10M!");
        }
        // 上传文件名格式不正确
        if (fileName.lastIndexOf(".") != -1 && !".xlsx".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            throw new BusinessException(ErrorCode.OPERATION_EXCEL_ERROR, "文件名格式不正确, 请使用后缀名为.XLSX的文件");
        }

        //读取数据
        ExcelUtil.read07BySax(file.getInputStream(), 0, createRowHandler());
        //去除excel中的第一行数据
        lineList.remove(0);

        //将数据封装到list<Map>中
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < lineList.size(); i++) {
            if (null != lineList.get(i)) {
                Map<String, Object> hashMap = new HashMap<>();
                for (int j = 0; j < columNames.length; j++) {
                    Object property = lineList.get(i).get(j);
                    hashMap.put(columNames[j].toString(), property);
                }
                dataList.add(hashMap);
            } else {
                break;
            }
        }
        return dataList;
    }

    /**
     * 通过实现handle方法编写我们要对每行数据的操作方式
     */
    private static RowHandler createRowHandler() {
        //清空一下集合中的数据
        lineList.removeAll(lineList);
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List rowlist) {
                //将读取到的每一行数据放入到list集合中
                JSONArray jsonObject = new JSONArray(rowlist);
//                lineList.add(jsonObject.toList(Object.class));
            }
        };
    }



//    public String impCars(String fileName) {
//        if (StringUtils.isEmpty(fileName)) {
//            return "无效文件";
//        } else {
//                int successCnt = 0;
//                int dataErrCnt = 0;
//                try {
//                    Workbook rwb = Workbook.getWorkbook(new ByteArrayInputStream(
//                            HttpClientUtils.getContent(BaseUtils.getDownloadUrlByUUID(fileName, 0) )));
//                    Sheet rs = rwb.getSheets()[0];
//                    if (rs.getRows() > 10001) {// 防止导入过慢 限制导入1000条数据
//                        return "每次只能导入10000条";
//                    }
//                    List<Map<String, Object>> importCarMapList = new ArrayList<Map<String, Object>>();
//
//                    //获取部门
//                    Map<String, Map<String, Object>> orgMap = userService.getOrgInfoByComoanyId(companyId);
//
//                    Set<String> plateNoSet = new TreeSet<>();
//                    Set<String> vinSet = new TreeSet<>();
//                    Set<String> numSet = new TreeSet<>();
//
//                    List<String> errorInfo = new ArrayList<>();
//
//                    for (int i = 1; i < rs.getRows(); i++) {
//                        int n = i + 1;
//                        Cell[] cells = rs.getRow(i);
//                        if (cells.length >= 2) {
//                            String plateNo = cells[0].getContents();
//                            String vin = cells[1].getContents();
//                            String num = "";
//                            if(cells.length > 2) {
//                                num = cells[2].getContents();
//                            }
//                            String orgName = "";
//                            if(cells.length == 4) {
//                                orgName = cells[3].getContents();
//                            }
//
//                            String errStr = "";
//
//                            if (StringUtils.isBlank(plateNo) || StringUtils.isBlank(vin)) {
//                                errStr = "第" + n + "行，车牌或VIN码为空。";
//                                errorInfo.add(errStr);
//                                continue;
//                            }else{
//
//                                if(plateNoSet.contains(plateNo)) {
//                                    errStr = "第" + n + "行，车牌数据重复。";
//                                    errorInfo.add(errStr);
//                                    continue;
//                                }
//                                if (!NumericalUtils.isCarNo(plateNo)){
//                                    errStr = "第" + n + "行，车牌格式不正确。";
//                                    errorInfo.add(errStr);
//                                    continue;
//                                }
//
//                                if(vinSet.contains(vin)) {
//                                    errStr = "第" + n + "行，VIN码数据重复。";
//                                    errorInfo.add(errStr);
//                                    continue;
//                                }
//
//                                if(numSet.contains(num)) {
//                                    errStr = "第" + n + "行，自编码数据重复。";
//                                    errorInfo.add(errStr);
//                                    continue;
//                                }
//
//                                if (carService.getCarByPlateNoOrVin(plateNo, null, null) > 0) {
//                                    errStr = "第" + n + "行，车牌已添加。";
//                                    errorInfo.add(errStr);
//                                    continue;
//                                }
//
//                                if(carService.getCarByPlateNoOrVin(null, vin, null) > 0){
//                                    errStr = "第" + n + "行，VIN码已添加。";
//                                    errorInfo.add(errStr);
//                                    continue;
//                                }
//
//                                if (!Pattern.compile("^[A-Z\\d]{17}$").matcher(vin).matches()) {
//                                    errStr = "第" + n + "行，VIN码格式不正确。";
//                                    errorInfo.add(errStr);
//                                    continue;
//                                }
//
//                                if (num != "" && carService.getCarByNum(num, companyId, null) > 0) {
//                                    errStr = "第" + n + "行，自编码已添加。";
//                                    errorInfo.add(errStr);
//                                    continue;
//                                }
//
//                                if(orgMap != null) {
//                                    if(orgName != "") {
//                                        if(!orgMap.containsKey(orgName)) {
//                                            errStr = "第" + n + "行，部门不存在。";
//                                            errorInfo.add(errStr);
//                                            continue;
//                                        } else if (Integer.parseInt(orgMap.get(orgName).get("cnt").toString()) > 1) {
//                                            errStr = "第" + n + "行，部门重复。";
//                                            errorInfo.add(errStr);
//                                            continue;
//                                        }
//                                    }
//                                }
//
//                                Map<String, Object> importCarMap = new HashMap<String, Object>();
//                                importCarMap.put("plateNo", plateNo);
//                                importCarMap.put("vin", vin);
//                                importCarMap.put("num", num);
//                                importCarMap.put("org", orgMap.get(orgName) == null ? null : orgMap.get(orgName).get("ID"));
//
//                                plateNoSet.add(plateNo);
//                                vinSet.add(vin);
//                                if(num != "") {
//                                    numSet.add(num);
//                                }
//                                importCarMapList.add(importCarMap);
//
//                                successCnt++;
//
//                            }
//                        } else {
//                            String errStr = "";
//                            //return "第" + n + "行，输入信息不完整。";
//                            errStr = "第" + n + "行，输入信息不完整。";
//                            errorInfo.add(errStr);
//                        }
//                    }
//                    if (!importCarMapList.isEmpty()) {
//                        final List<Map<String, Object>> importDatas = importCarMapList;
//                        final String nameF = fileName;
//                        // 多线程进行数据导入
//                        ThreadPoolUtils.getQueueThreadPool().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                int addCountSum = 0;
//                                for (Map<String, Object> importData : importDatas) {
//                                    int count = 0;
//                                    try {
//                                        String plateNo = importData.get("plateNo").toString();
//                                        String vin = importData.get("vin").toString();
//                                        String num = importData.get("num") == null ? null : importData.get("num").toString();
//                                        String org = importData.get("org") == null ? null : importData.get("org").toString();
//                                        //数据库输入
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    addCountSum += count;
//                                }
//                            }
//                        });
//                    }
//                    dataErrCnt = rs.getRows() - 1 - successCnt;
//                    return "成功导入" + successCnt + "辆车辆,有"+dataErrCnt+"条错误数据,错误信息如下: ");
//                } catch (FileNotFoundException e) {
//                    return "文件未找到";
//                } catch (BiffException e) {
//                    return "文件格式错误";
//                } catch (IOException e) {
//                    return "导入出错";
//                } finally {
//                }
//
//
//        }
//    }
}
