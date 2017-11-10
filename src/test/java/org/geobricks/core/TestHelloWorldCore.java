package org.geobricks.core;

import junit.framework.TestCase;

public class TestHelloWorldCore extends TestCase {

    private HelloWorldCore c;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        c = new HelloWorldCore();
    }

    public void testSayHello() {
        assertEquals(c.sayHello("Guido"), "Hello Guido!");
    }

    public void testSayHelloJSON() {
        assertEquals(c.sayHelloJSON("Guido"), "{\"hello\":\"Guido\"}");
    }

}