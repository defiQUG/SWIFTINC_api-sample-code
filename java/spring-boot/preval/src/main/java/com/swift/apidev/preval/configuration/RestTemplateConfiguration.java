package com.swift.apidev.preval.configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swift.apidev.preval.interceptor.AuthorizationInterceptor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(CloseableHttpClient httpClient,
            AuthorizationInterceptor authorizationInterceptor,
            ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        return new RestTemplateBuilder()
                .requestFactory(() -> new BufferingClientHttpRequestFactory(
                        new HttpComponentsClientHttpRequestFactory(httpClient)))
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .additionalInterceptors(authorizationInterceptor).build();
    }

    @Bean
    public JsonNullableModule jsonNullableModule() {
        return new JsonNullableModule();
    }
}
