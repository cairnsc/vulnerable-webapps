package todoList.controllers;

import com.thoughtworks.adtd.csrf.token.CsrfTokenTest;
import com.thoughtworks.adtd.csrf.token.CsrfTokenTestOrchestrator;
import com.thoughtworks.adtd.csrf.token.ResponseStatusValidator;
import com.thoughtworks.adtd.csrf.token.ResponseValidator;
import com.thoughtworks.adtd.http.*;
import com.thoughtworks.adtd.springframework.SpringTestWebProxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import todoList.config.WebSecurityConfig;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class, WebSecurityConfig.class })
@WebAppConfiguration
@WithMockUser
public class AddItemControllerCsrfTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private MockHttpSession session;
    private SpringTestWebProxy webProxy;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        session = new MockHttpSession();
        webProxy = new SpringTestWebProxy(mockMvc).withSession(session);
    }

    @Test
    public void shouldNotBeSusceptibleToCsrf() throws Exception {
        RequestInfo requestInfo = retrieveForm();
        RequestParameters requestParameters = requestInfo.getRequestParameters();
        requestParameters.setParam("description", "test");
        requestParameters.setParam("dueDate", "01/01/2015");
        ResponseValidator validator = new ResponseStatusValidator(302, 403);
        CsrfTokenTestOrchestrator orchestrator = new CsrfTokenTestOrchestrator(requestInfo, validator, "_csrf");

        while (orchestrator.hasNext()) {
            CsrfTokenTest csrfTest = orchestrator.next();
            csrfTest.prepare().execute(webProxy);
            csrfTest.assertResponse();
        }
    }

    private RequestInfo retrieveForm() throws Exception {
        FormRetrieveRequest formRetrieveRequest = new FormRetrieveRequest("/addItem").withCsrfToken("_csrf");
        formRetrieveRequest.prepare()
                .method("GET").uri("/addItem")
                .execute(webProxy);
        return formRetrieveRequest.getForm().getRequestInfo();
    }
}