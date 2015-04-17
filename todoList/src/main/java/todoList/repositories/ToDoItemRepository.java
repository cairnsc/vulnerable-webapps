package todoList.repositories;

import org.springframework.data.repository.CrudRepository;
import todoList.entities.ToDoItem;

public interface ToDoItemRepository extends CrudRepository<ToDoItem, Long> {
}
