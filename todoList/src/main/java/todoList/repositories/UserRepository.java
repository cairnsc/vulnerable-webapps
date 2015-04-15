package todoList.repositories;

import org.springframework.data.repository.CrudRepository;
import todoList.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
