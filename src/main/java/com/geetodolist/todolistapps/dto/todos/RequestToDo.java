package com.geetodolist.todolistapps.dto.todos;
import com.geetodolist.todolistapps.constant.ProgressTodoEnum;
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
    @Size(max = 50, min = 8, message = "To-Do tittle at least 8 char")
    private String titleTodo;

    @NotBlank(message = "Description To-Do name must not be null")
    @Size(max = 250, min = 15, message = "To-Do Description at least 15 char")
    private String descTodo;

    private String  progressTodo = ProgressTodoEnum.TO_DO.getCode();

}
