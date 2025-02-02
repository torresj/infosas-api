package com.torresj.infosas.services;


import com.torresj.infosas.dtos.QueueMessage;

public interface ProducerService {
    void sendMessage(QueueMessage message);
}
