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

        Student student = new Student(1, "tom", 21);
        Entity<Student> json = Entity.json(student);

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        Entity<Form> entityForm = Entity.form(formData);
        //client.target("").request()..invoke();
        Response postRes = client.target("http://127.0.0.1:8080/save").request().post(json);
        System.out.println(postRes.readEntity(String.class));
    }


    public static class Student {

        private Integer id;

        private String name;

        private Integer age;

        public Student() {
        }

        public Student(Integer id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
