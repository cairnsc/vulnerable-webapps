package todoList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import todoList.dto.NewToDoItem;
import todoList.entities.ToDoItem;
import todoList.entities.User;
import todoList.repositories.ToDoItemRepository;
import todoList.repositories.UserRepository;

import java.security.Principal;

@Controller
public class AddItemController {

    public final static String TEMPLATE_NAME = "addItem";
    private ToDoItemRepository toDoItemRepository;
    private UserRepository userRepository;

    @Autowired
    public AddItemController(ToDoItemRepository toDoItemRepository, UserRepository userRepository) {
        this.toDoItemRepository = toDoItemRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public String index(Model model) {
        return TEMPLATE_NAME;
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public String create(NewToDoItem newToDoItemData, Model model, Principal principal) {
        User user = getCurrentUser(principal);
        ToDoItem toDoItem = new ToDoItem(user, newToDoItemData.getDescription(), newToDoItemData.getDueDate());
        toDoItemRepository.save(toDoItem);
        return "redirect:/";
    }

    private User getCurrentUser(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }

}
