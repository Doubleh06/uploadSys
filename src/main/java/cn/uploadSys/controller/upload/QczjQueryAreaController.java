package cn.uploadSys.controller.upload;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.uploadSys.controller.BaseController;

import cn.uploadSys.entity.VO.QczjQueryAreaVO;
import cn.uploadSys.service.upload.QczjQueryAreaService;
import cn.uploadSys.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj")
public class QczjQueryAreaController extends BaseController {

    @Autowired
    private QczjQueryAreaService qczjQueryAreaService;


    @GetMapping("/exportArea")
    public void exportArea(HttpServletResponse response) throws Exception {
        qczjQueryAreaService.exportArea(response);

    }

}

