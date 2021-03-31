package org.geektimes.rest.demo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class RestClientDemo {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.Builder
                .get();                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);

        Object object = new Object();
        Entity<Object> json = Entity.json(object);

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        Entity<Form> entityForm = Entity.form(formData);
        client.target("").request().buildPost(null).invoke();
        //client.target("").request().post(entityForm);

    }
}
