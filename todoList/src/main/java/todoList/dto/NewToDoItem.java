package todoList.dto;

import java.util.Date;

public class NewToDoItem {

    private String description;
    private Date dueDate;

    protected NewToDoItem() {
    }

    public NewToDoItem(String description, Date dueDate) {
        this.description = description;
        this.dueDate = dueDate;
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
