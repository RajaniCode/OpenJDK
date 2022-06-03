package com.sample.client;

import com.sample.ws.HelloWorld;
import com.sample.ws.HelloWorldImplService;

public class HelloWorldClient {
    public static void main(String[] args) {
        HelloWorldImplService helloService = new HelloWorldImplService();
        HelloWorld hello = helloService.getHelloWorldImplPort();
        System.out.println(hello.getHelloWorldAsString("Java Web Service Client via wsimport"));
    }
}