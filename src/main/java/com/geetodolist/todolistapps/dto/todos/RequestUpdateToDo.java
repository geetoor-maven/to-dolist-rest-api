package com.geetodolist.todolistapps.dto.todos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUpdateToDo {
    @Size(max = 50, min = 8, message = "To-Do tittle at least 8 char")
    private String titleTodo;
    @Size(max = 250, min = 15, message = "To-Do Description at least 15 char")
    private String descTodo;
}
