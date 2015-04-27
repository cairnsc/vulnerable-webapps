package todoList.controllers;

import com.thoughtworks.adtd.xss.XssTest;
import com.thoughtworks.adtd.xss.XssTestIteratorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import todoList.entities.ToDoItem;
import todoList.entities.User;
import todoList.repositories.ToDoItemRepository;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@RunWith(Parameterized.class)
public class MainControllerXssTests {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return XssTestIteratorImpl.asIterableOfArrays();
    }

    private TestContextManager testContextManager;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ToDoItemRepository toDoItemRepository;
    private MockMvc mockMvc;
    private XssTest xssTest;

    public MainControllerXssTests(XssTest xssTest) {
        this.xssTest = xssTest;
    }

    @Before
    public void setUp() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldNotBeSusceptibleToXss() throws Exception {
        User user = mock(User.class);
        ToDoItem toDoItem = new ToDoItem(user, xssTest.getTestPattern(), new Date());
        when(toDoItemRepository.findAll()).thenReturn(Collections.singletonList(toDoItem));

        MvcResult mvcResult = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("toDoItemList"))
//                .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(xssTest.matches(content)).isFalse();
    }

}