package todoList.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import todoList.repositories.ToDoItemRepository;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MainControllerTests {

    @Mock
    private ToDoItemRepository toDoItemRepository;
    private MainController mainController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mainController = new MainController(toDoItemRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    public void shouldGetToDoList() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

}
