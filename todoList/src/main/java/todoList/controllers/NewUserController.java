package todoList.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import todoList.dto.NewUser;
import todoList.entities.User;
import todoList.repositories.UserRepository;
import todoList.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewUserController {

    public final static String TEMPLATE_NAME = "newUser";
    private UserRepository userRepository;

    @Autowired
    public NewUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String index(Model model) {
        return TEMPLATE_NAME;
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String create(NewUser newUserData, Model model) {
        List<String> errorList = validate(newUserData, model);
        if (!errorList.isEmpty()) {
            model.addAttribute("errorList", errorList);
            return TEMPLATE_NAME;
        }

        User user = new User(newUserData.getUsername(), newUserData.getPassword());
        userRepository.save(user);
        SecurityUtil.logInUser(user);
        return "redirect:/";
    }

    private List<String> validate(NewUser newUserData, Model model) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(newUserData.getUsername())) {
            errorList.add("Username can not be empty");
        }

        if (StringUtils.isBlank(newUserData.getPassword())) {
            errorList.add("Password can not be empty");
        }
        return errorList;
    }

}
