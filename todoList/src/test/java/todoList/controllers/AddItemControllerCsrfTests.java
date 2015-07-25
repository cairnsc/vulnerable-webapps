package todoList.controllers;

import com.thoughtworks.adtd.csrf.token.CsrfTokenTest;
import com.thoughtworks.adtd.csrf.token.CsrfTokenTestIteratorImpl;
import com.thoughtworks.adtd.html.FormData;
import com.thoughtworks.adtd.http.Request;
import com.thoughtworks.adtd.http.Response;
import com.thoughtworks.adtd.http.ResponseValidator;
import com.thoughtworks.adtd.http.WebProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import todoList.adtdProxy.MockMvcAdtdWebProxyHack;
import todoList.config.WebSecurityConfig;

import static com.thoughtworks.adtd.http.ResponseConditionFactory.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ContextConfiguration(classes = { TestConfig.class, WebSecurityConfig.class })
@WebAppConfiguration
@RunWith(Parameterized.class)
@WithMockUser
public class AddItemControllerCsrfTests {

    private TestContextManager testContextManager;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private WebProxy proxy;
    private final CsrfTokenTest csrfTest;

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        ResponseValidator validator = new ResponseValidator() {
            @Override
            public boolean validate(Request request, Response response) {
                String location = response.getHeader("Location");
                assertThat(location).isEqualTo("/");
                return true;
            }
        };
        return CsrfTokenTestIteratorImpl.asIterableOfArrays("/addItem", "_csrf", validator);
    }

    public AddItemControllerCsrfTests(CsrfTokenTest csrfTest) {
        this.csrfTest = csrfTest;
    }

    @Before
    public void setUp() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        // see: SPR-7731
        // also:
        //  http://minds.coremedia.com/2014/08/28/junit-runwith-springjunit4classrunner-vs-parameterized/
        //  https://github.com/mmichaelis/spring-aware-rule/blob/master/src/main/java/com/coremedia/testing/junit/SpringAware.java
        testContextManager.beforeTestMethod(this, getClass().getMethod("shouldNotBeSusceptibleToCsrf"));

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        MockHttpSession session = new MockHttpSession();
        proxy = new MockMvcAdtdWebProxyHack(mockMvc, session);
    }

    @After
    public void tearDown() throws Exception {
        testContextManager.afterTestMethod(this, getClass().getMethod("shouldNotBeSusceptibleToCsrf"), null);
    }

    @Test
    public void shouldNotBeSusceptibleToCsrf() throws Exception {
        csrfTest.prepareRetrieve().uri("/addItem").execute(proxy);
        FormData formData = csrfTest.getFormData();
        formData.setFormField("description", "test");
        formData.setFormField("dueDate", "01/01/2015");

        csrfTest.prepareSubmit().uri("/addItem").expect(status().is(302)).execute(proxy);

        csrfTest.assertResponse();
    }



}