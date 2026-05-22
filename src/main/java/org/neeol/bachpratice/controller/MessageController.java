package org.neeol.bachpratice.controller;

import org.neeol.bachpratice.dto.MessageLogDTO;
import org.neeol.bachpratice.model.MessageLogs;
import org.neeol.bachpratice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/messages")
public class MessageController {


    @Autowired
    private MessageService messageService;

    @PostMapping("/batch")
    public boolean batch(List<MessageLogDTO> messages){
      try{
          messageService.processBatch(messages);
          return true;
      }catch(Exception e){
          return false;
      }
    };

}
