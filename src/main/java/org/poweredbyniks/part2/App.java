package org.poweredbyniks.part2;

import org.poweredbyniks.part2.DemoPart2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        new DemoPart2().runDemo();
    }
}
