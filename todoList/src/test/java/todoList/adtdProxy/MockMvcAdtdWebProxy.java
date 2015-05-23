package todoList.adtdProxy;

import com.thoughtworks.adtd.http.Request;
import com.thoughtworks.adtd.http.Response;
import com.thoughtworks.adtd.http.WebProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

public class MockMvcAdtdWebProxy implements WebProxy {

    private MockMvc mockMvc;

    public MockMvcAdtdWebProxy(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Override
    public Response execute(Request request) throws Exception {
        MockMvcAdtdRequestBuilder requestBuilder = new MockMvcAdtdRequestBuilder(request);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        return new MvcResultAdtdResponse(mvcResult);
    }

}
