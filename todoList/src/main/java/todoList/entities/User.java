package todoList.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, length = 64, nullable = false)
    private String username;
    @Column(length = 255, nullable = false)
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return !(toDoItems != null ? !toDoItems.equals(user.toDoItems) : user.toDoItems != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (toDoItems != null ? toDoItems.hashCode() : 0);
        return result;
    }

}
