package org.poweredbyniks.part2;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class DemoPart2 {

    public void runDemo() throws InterruptedException {
        Mono<String> user = Mono.just("Alice");
        Mono<String> greeting = user
                .flatMap(u -> Mono.just("Hello, " + u + "!"))
                .delayElement(Duration.ofMillis(500));

        greeting.subscribe(System.out::println);

        // Keep JVM alive to let async work complete
        Thread.sleep(1000);

        // Flux example
        Flux.just("Java", "Spring", "Vert.x")
                .filter(s -> s.contains("."))
                .switchIfEmpty(Flux.just("No matches"))
                .subscribe(System.out::println);
    }
}
