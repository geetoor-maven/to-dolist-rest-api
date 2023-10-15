package com.geetodolist.todolistapps.dto.todos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestToDo {

    @NotBlank(message = "Title To-Do name must not be null")
    @Size(max = 50, min = 10, message = "To-Do tittle at least 10 char")
    private String titleTodo;

    @NotBlank(message = "Description To-Do name must not be null")
    @Size(max = 250, min = 20, message = "To-Do Description at least 20 char")
    private String descTodo;

}
