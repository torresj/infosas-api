package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QueueMessage implements Serializable {
    private long chatId;
    private String error;
    private MessageType type;
}
