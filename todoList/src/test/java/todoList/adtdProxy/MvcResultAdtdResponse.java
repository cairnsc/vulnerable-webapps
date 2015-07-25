package todoList.adtdProxy;

import com.thoughtworks.adtd.http.Response;
import org.springframework.test.web.servlet.MvcResult;

public class MvcResultAdtdResponse implements Response {

    private MvcResult result;

    public MvcResultAdtdResponse(MvcResult result) {
        this.result = result;
    }

    @Override
    public int getStatus() {
        return result.getResponse().getStatus();
    }

    @Override
    public String getHeader(String name) {
        return result.getResponse().getHeader(name);
    }

    @Override
    public String getBody() throws Exception {
        return result.getResponse().getContentAsString();
    }

}
