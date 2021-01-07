package com.demo.client.integration;

import com.demo.client.exception.DemoCode;
import com.demo.client.exception.DemoException;
import com.demo.service.model.IntegrationRes;
import com.demo.service.utils.JsonUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        IntegrationRes integrationRes = JsonUtil.jsonToObject(response.body().toString(), IntegrationRes.class);
        log.error("integration methodKey: {}", methodKey );
        log.error("integration Status code: {}, info: {}, body: {}", response.status(), response.request().toString(), JsonUtil.objectToJson(integrationRes));
        if(integrationRes != null){
            return new DemoException(DemoCode.INTEGRATION_ERROR, JsonUtil.objectToJson(integrationRes));
        } else {
            return new DemoException(DemoCode.INTEGRATION_ERROR);
        }
    }
}
