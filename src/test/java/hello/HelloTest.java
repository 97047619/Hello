package hello;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class HelloTest {
    private Hello hello = new Hello();

    @Test
    public void helloSaysHello() {
        assertThat(hello.sayHello(), containsString("Hello"));
    }
}
