package com.example.sp11.fallback;

import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class OrderFallback implements FallbackProvider {
    /**
     * 返回一个服务id，表示当前降级类是针对那个服务的降级
     * @return
     */
    @Override
    public String getRoute() {
        return "order-service";
//        return "*";  //针对所有服务都应用当前降级类
//        return null; //同样针对所有服务
    }

    /**
     * 向客户端返回的降级响应，封装到response对象
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return response();
    }

    private ClientHttpResponse response(){
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                //JsonResult --> {code:400,msg:not log in, data:}
                String json = JsonResult.err().msg("调用订单服务失败").toString();

                return new ByteArrayInputStream(json.getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                //Context-Type:application/json
                HttpHeaders h = new HttpHeaders();
                h.setContentType(MediaType.APPLICATION_JSON);
                return h;
            }
        };
    }
}
