package com.practice.dtos;
import lombok.Getter;
@Getter
public enum IncidentStatusValue {
    PENDING("PENDING"),
    CLOSED("CLOSED");
    private final String value;
    IncidentStatusValue(String value){
        this.value = value;
    }

}
