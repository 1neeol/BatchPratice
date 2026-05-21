package org.neeol.bachpratice.controller;

import org.neeol.bachpratice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/messages")
public class MessageController {


    @Autowired
    private MessageService messageService;

    @PostMapping
    public boolean

}
