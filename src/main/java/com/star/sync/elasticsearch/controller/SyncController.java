package com.star.sync.elasticsearch.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.sync.elasticsearch.model.request.SyncByTableRequest;
import com.star.sync.elasticsearch.model.response.Response;
import com.star.sync.elasticsearch.rabbitmq.Sender;
import com.star.sync.elasticsearch.service.SyncService;
import com.star.sync.elasticsearch.util.JsonUtil;

/**
 * @author <a href="mailto:wangchao.star@gmail.com">wangchao</a>
 * @version 1.0
 * @since 2017-08-29 19:32:00
 */
@Controller
@RequestMapping("/sync")
public class SyncController {
    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);

    @Resource
    private SyncService syncService;
    
    @Autowired
    private Sender sender;

    /**
     * 通过库名和表名全量同步数据
     *
     * @param request 请求参数
     */
    @RequestMapping("/byTable")
    @ResponseBody
    public String syncTable(@Validated SyncByTableRequest request) {
        logger.debug("request_info: " + JsonUtil.toJson(request));
        String response = Response.success(syncService.syncByTable(request)).toString();
        logger.debug("response_info: " + JsonUtil.toJson(request));
        return response;
    }
    
    @RequestMapping("/send")
    public String send(String msg){
    	sender.send(msg);
    	return msg;
    }
}
