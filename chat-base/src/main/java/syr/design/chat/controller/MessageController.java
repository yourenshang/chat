package syr.design.chat.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import syr.design.chat.enums.EnumMessageSendType;
import syr.design.chat.model.Message;
import syr.design.chat.model.Result;
import syr.design.chat.service.IMessageService;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author syr
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController{

    @Resource
    private IMessageService messageService;

    @GetMapping(value = "/messageInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result messageInfo(@RequestParam("userId") Long userId){
        List<Message> list = this.messageService.getMessage(userId);
        Map<String, Map<String, Object>> result = new HashMap<>();
        for (Message message : list){
            Map<String, Object> one = new HashMap<>();
            if (message.getType().equals(EnumMessageSendType.user.value())){
                if (message.getFromUserId().equals(userId)) {
                    one.put("name", message.getToUserName());
                    one.put("type", EnumMessageSendType.user.value());
                    one.put("id", message.getToUserId().toString());
                }else {
                    one.put("name", message.getFromUserName());
                    one.put("type", EnumMessageSendType.user.value());
                    one.put("id", message.getFromUserId().toString());
                }
            }else {
                one.put("name", message.getToGroupName());
                one.put("type", EnumMessageSendType.group.value());
                one.put("id", message.getToGroupId().toString());
            }
            result.put(one.get("name").toString(), one);
        }
        List<Map<String, Object>> resultlist = new ArrayList<>();
        Set<String> keys = result.keySet();
        for (String key : keys){
            resultlist.add(result.get(key));
        }
        return result(resultlist);
    }

    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result message(@RequestParam("userId") Long userId, @RequestParam("type") Integer type, @RequestParam("id") Long id){
        List<Message> result;
        if (type.equals(EnumMessageSendType.user.value())){
            result = this.messageService.getMessageByUser(userId, id);
        }else {
            result = this.messageService.getMessageByGroup(userId, id);
        }
        return result(result);
    }

}

