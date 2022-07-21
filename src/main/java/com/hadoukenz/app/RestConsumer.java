package com.hadoukenz.app;

import com.hadoukenz.app.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestConsumer {

    private static final String URL = "http://94.198.50.185:7081/api/users/";

    private static User user = new User();
    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        String cookies = getAllUsers();
        System.out.println(addUser(cookies) + " " + updateUser(cookies) + " " + deleteUser(cookies, 3L));
    }

    private static String getAllUsers() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        restTemplate.exchange(URL, HttpMethod.GET, httpEntity, String.class);

        return restTemplate.exchange(
                URL, HttpMethod.GET, httpEntity, String.class)
                .getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }

    private static String addUser(String cookie) {
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 23);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        return restTemplate.exchange(
                URL, HttpMethod.POST, httpEntity, String.class).getBody();
    }

    private static String updateUser(String cookie) {

        user.setName("Thomas");
        user.setLastName("Shelby");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        return restTemplate.exchange(
                URL, HttpMethod.PUT, httpEntity, String.class).getBody();
    }

    private static String deleteUser(String cookie, Long id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        return restTemplate.exchange(
                URL + id.toString(), HttpMethod.DELETE, httpEntity, String.class).getBody();
    }
}