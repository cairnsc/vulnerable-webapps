package todoList.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private Collection<ToDoItem> toDoItems;

    protected User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<ToDoItem> getToDoItems() {
        return toDoItems;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, username='%s']", id, username);
    }

}
