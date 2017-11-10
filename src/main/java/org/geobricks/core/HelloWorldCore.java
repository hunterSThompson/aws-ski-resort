package org.geobricks.core;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldCore {

    private Gson g;

    public HelloWorldCore() {
        this.setG(new Gson());
    }

    public String sayHello(String name) {
        return "Hello " + name + "!";
    }

    public String sayHelloJSON(String name) {
        Map<String, String> m = new HashMap<>();
        m.put("hello", name);
        return this.getG().toJson(m);
    }

    public Gson getG() {
        return g;
    }

    public void setG(Gson g) {
        this.g = g;
    }

}
