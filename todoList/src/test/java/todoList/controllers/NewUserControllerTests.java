package todoList.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.ui.Model;
import todoList.entities.User;
import todoList.dto.NewUser;
import todoList.repositories.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class NewUserControllerTests {

    @Mock
    private UserRepository userRepository;
    private NewUserController newUserController;
    private Model model;
    @Captor
    private ArgumentCaptor<List<String>> errorListCaptor;

    @Before
    public void setUp() {
        initMocks(this);
        newUserController = new NewUserController(userRepository);
        model = mock(Model.class);
    }

    @Test
    public void shouldSaveNewUserAndRedirect() {
        String username = "username";
        String password = "password";
        NewUser newUserData = new NewUser(username, password);

        String result = newUserController.create(newUserData, model);

        User user = new User(username, password);
        verify(userRepository).save(user);
        assertThat(result).isEqualTo("redirect:/");
    }

    @Test
    public void shouldRaiseErrorForEmptyUsername() {
        NewUser newUserData = new NewUser(null, "password");

        String result = newUserController.create(newUserData, model);

        verify(model).addAttribute(eq("errorList"), errorListCaptor.capture());
        List<String> errorList = errorListCaptor.getValue();
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList).contains("Username can not be empty");
        assertThat(result).isEqualTo(NewUserController.TEMPLATE_NAME);
    }

    @Test
    public void shouldRaiseErrorForEmptyPassword() {
        NewUser newUserData = new NewUser("username", null);

        String result = newUserController.create(newUserData, model);

        verify(model).addAttribute(eq("errorList"), errorListCaptor.capture());
        List<String> errorList = errorListCaptor.getValue();
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList).contains("Password can not be empty");
        assertThat(result).isEqualTo(NewUserController.TEMPLATE_NAME);
    }

}
