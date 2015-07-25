package todoList.adtdProxy;

import com.thoughtworks.adtd.http.Request;
import com.thoughtworks.adtd.http.Response;
import com.thoughtworks.adtd.http.WebProxy;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MockMvcAdtdWebProxyHack implements WebProxy {

    private MockMvc mockMvc;
    private final MockHttpSession session;

    public MockMvcAdtdWebProxyHack(MockMvc mockMvc, MockHttpSession session) {
        this.mockMvc = mockMvc;
        this.session = session;
    }

    @Override
    public Response execute(Request request) throws Exception {
        MockMvcAdtdRequestPostProcessor postProcessor = new MockMvcAdtdRequestPostProcessor(request);
        MvcResult mvcResult  = mockMvc.perform(
                MockMvcRequestBuilders.get(new URI("/"))
                        .session(session).
                        with(postProcessor)
        ).andDo(print()).andReturn();
        return new MvcResultAdtdResponse(mvcResult);
    }

}
