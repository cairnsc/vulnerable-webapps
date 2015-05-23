package todoList.adtdProxy;

import com.thoughtworks.adtd.http.Request;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.RequestBuilder;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

public class MockMvcAdtdRequestBuilder implements RequestBuilder {

    private final Request request;

    public MockMvcAdtdRequestBuilder(Request request) {
        this.request = request;
    }

    @Override
    public MockHttpServletRequest buildRequest(ServletContext servletContext) {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest(servletContext);
        setRequestLine(mockRequest);
        setHeaders(mockRequest);

        return mockRequest;
    }

    private void setRequestLine(MockHttpServletRequest mockRequest) {
        mockRequest.setMethod(request.getMethod());
        mockRequest.setRequestURI(request.getUri());
    }

    private void setHeaders(MockHttpServletRequest mockRequest) {
        for (Map.Entry<String, List<String>> entry : request.getHeaders().entrySet()) {
            String fieldName = entry.getKey();
            for (String fieldValue : entry.getValue()) {
                mockRequest.addHeader(fieldName, fieldValue);
            }
        }
    }

}
