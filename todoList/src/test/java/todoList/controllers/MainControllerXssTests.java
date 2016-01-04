package todoList.controllers;

import com.thoughtworks.adtd.injection.xss.XssTest;
import com.thoughtworks.adtd.injection.xss.XssTestOrchestrator;
import com.thoughtworks.adtd.injection.xss.strategies.TestStrategyIteratorInjected;
import com.thoughtworks.adtd.springframework.SpringTestWebProxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import todoList.entities.ToDoItem;
import todoList.entities.User;
import todoList.repositories.ToDoItemRepository;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class MainControllerXssTests {
    private TestContextManager testContextManager;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ToDoItemRepository toDoItemRepository;
    private MockMvc mockMvc;
    private SpringTestWebProxy webProxy;

    @Before
    public void setUp() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
        mockMvc = webAppContextSetup(webApplicationContext).build();
        webProxy = new SpringTestWebProxy(mockMvc);
    }

    @Test
    public void shouldNotBeSusceptibleToPersistentXss() throws Exception {
        TestStrategyIteratorInjected testStrategyIterator = new TestStrategyIteratorInjected();
        XssTestOrchestrator orchestrator = new XssTestOrchestrator(testStrategyIterator);

        while (orchestrator.hasNext()) {
            XssTest xssTest = orchestrator.next();
            ToDoItem toDoItemMock = new ToDoItem(mock(User.class), xssTest.getXssPayload().getPayload(), new Date());
            when(toDoItemRepository.findAll()).thenReturn(Collections.singletonList(toDoItemMock));

            xssTest.prepare().method("GET").uri("/").execute(webProxy);

            xssTest.assertResponse();
        }
    }
}