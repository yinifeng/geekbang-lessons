/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geektimes.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geektimes.rest.core.DefaultResponse;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * HTTP POST Method {@link Invocation}
 *
 * @author HUBO
 * @since
 */
class HttpPostInvocation implements Invocation {
    
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    private final URL url;

    private final MultivaluedMap<String, Object> headers;
    
    private final Entity<?> entity;
    
    private byte[] body;

    HttpPostInvocation(URI uri, Entity<?> entity,MultivaluedMap<String, Object> headers) {
        URI newUri = uri;
        this.headers = headers;
        this.entity = entity;
        try {
            if (entity != null) {
                Object content = this.entity.getEntity();
                String encoding = "UTF-8";
                Variant variant = this.entity.getVariant();
                if (variant != null && variant.getEncoding() != null) {
                    encoding = variant.getEncoding();
                }
                if (content != null) {
                    if (content instanceof byte[]) {
                        body = (byte[])content;
                    }else if(content instanceof String){
                        this.headers.putSingle("Content-Type",MediaType.APPLICATION_JSON);
                        body = ((String)content).getBytes(encoding);
                    }else if(content instanceof Form){
                        //拼接uri
                        Form form = (Form) content;
                        MultivaluedMap<String, String> stringStringMultivaluedMap = form.asMap();
                        //TODO 拼接参数
                        // htpp://127.0.0.1/save?a=123&b=456
                        
                    }else {
                        MediaType mediaType = this.entity.getMediaType();
                        if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType)) {
                            this.headers.putSingle("Content-Type",MediaType.APPLICATION_JSON);
                            body = OBJECTMAPPER.writeValueAsString(content).getBytes(encoding);
                        }else {
                            //TODO FORM DATA 转换
                        }
                    }
    
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            this.url = newUri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Invocation property(String name, Object value) {
        return this;
    }

    @Override
    public Response invoke() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setAllowUserInteraction(false);
            connection.setRequestMethod(HttpMethod.POST);
            setRequestHeaders(connection);
            if (body != null) {
                connection.setDoOutput(true);
                try(OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(body);
                }
            }
            // TODO Set the cookies
            int statusCode = connection.getResponseCode();
//            Response.ResponseBuilder responseBuilder = Response.status(statusCode);
//
//            responseBuilder.build();
            DefaultResponse response = new DefaultResponse();
            response.setConnection(connection);
            response.setStatus(statusCode);
            return response;
//            Response.Status status = Response.Status.fromStatusCode(statusCode);
//            switch (status) {
//                case Response.Status.OK:
//
//                    break;
//                default:
//                    break;
//            }

        } catch (IOException e) {
            // TODO Error handler
        }
        return null;
    }

    private void setRequestHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(headerName, headerValue.toString());
            }
        }
    }

    @Override
    public <T> T invoke(Class<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public Future<Response> submit() {
        return null;
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        return null;
    }
}
