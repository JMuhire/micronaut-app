package example.micronaut;

import io.micronaut.runtime.Micronaut;

import java.util.concurrent.CountDownLatch;

public class Application {

    static final CountDownLatch FINISH = new CountDownLatch(1);

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}