package com.geetodolist.todolistapps.constant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum ProgressTodoEnum {

    TO_DO("TO_DO", "To-Do"),
    IN_PROGRESS("IN_PROGRESS", "In Progress"),
    DONE("DONE", "Done");

    private String value;
    private String code;
    ProgressTodoEnum(String value, String code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public static String getProgressTodoEnum(String code){
        String value = code;
        List<ProgressTodoEnum> enums = new ArrayList<>(EnumSet.allOf(ProgressTodoEnum.class));
        for (ProgressTodoEnum todoEnum: enums){
            if (todoEnum.getCode().equalsIgnoreCase(value)){
                value = todoEnum.getValue();
                break;
            }
        }
        return value;
    }
}
