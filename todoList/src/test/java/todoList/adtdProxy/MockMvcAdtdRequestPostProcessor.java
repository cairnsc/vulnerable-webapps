package todoList.adtdProxy;

import com.thoughtworks.adtd.http.Request;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.Map;

public class MockMvcAdtdRequestPostProcessor implements RequestPostProcessor {

    private final Request request;

    public MockMvcAdtdRequestPostProcessor(Request request) {
        this.request = request;
    }

    @Override
    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        setRequestLine(request);
        setHeaders(request);
        setParams(request);
        return request;
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

    private void setParams(MockHttpServletRequest mockRequest) {
        for (Map.Entry<String, List<String>> entry : request.getParams().entrySet()) {
            String fieldName = entry.getKey();
            for (String fieldValue : entry.getValue()) {
                mockRequest.addParameter(fieldName, fieldValue);
            }
        }
    }

}
