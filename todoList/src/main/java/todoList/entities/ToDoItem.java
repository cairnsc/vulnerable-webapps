package todoList.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @Column(length = 255, nullable = false)
    private String description;
    @Column(nullable = false)
    private Date dueDate;

    protected ToDoItem() {
    }

    public ToDoItem(User user, String description, Date dueDate) {
        this.user = user;
        this.description = description;
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}
