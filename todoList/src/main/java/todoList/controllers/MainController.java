package todoList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import todoList.entities.ToDoItem;
import todoList.repositories.ToDoItemRepository;

@Controller
public class MainController {

    private ToDoItemRepository toDoItemRepository;

    @Autowired
    public MainController(ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("toDoItemList", toDoItemRepository.findAll());
        return "toDoItemList";
    }

}
