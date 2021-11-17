package com.dmbb.cafe.model.entity;

import com.dmbb.cafe.model.enums.AuditEventType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuditEvent {

    private Date dateTime;

    private AuditEventType type;

    private String workerName;

    private String info;

}
