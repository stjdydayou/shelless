package com.axungu.sftp.web.controller;

import com.axungu.sftp.core.mappers.GroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/7 17:38
 */
@Slf4j
@Controller
@RequestMapping("/sftp/info")
public class SftpInfoController {

    @Autowired
    private GroupMapper hostGroupMapper;

    @ResponseBody
    @GetMapping("/index.htm")
    public Object index(ModelMap modelMap) {
        return this.hostGroupMapper.findAll();
    }

}
