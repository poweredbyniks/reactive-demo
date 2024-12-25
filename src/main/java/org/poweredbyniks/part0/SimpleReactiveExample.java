package org.poweredbyniks.part0;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SimpleReactiveExample {

    public static void main(String[] args) {
        Publisher<Integer> publisher = new SimplePublisher();
        Subscriber<Integer> subscriber = new SimpleSubscriber();
        publisher.subscribe(subscriber);
    }
}

// A simple Publisher that emits numbers from 1 to 5
class SimplePublisher implements Publisher<Integer> {
    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        // Create a Subscription and pass it to the Subscriber
        SimpleSubscription subscription = new SimpleSubscription(subscriber);
        subscriber.onSubscribe(subscription);
    }
}

class SimpleSubscription implements Subscription {
    private final Subscriber<? super Integer> subscriber;
    private int currentValue = 1;
    private boolean isCanceled = false;

    public SimpleSubscription(Subscriber<? super Integer> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        // Emit 'n' items if not canceled
        for (int i = 0; i < n; i++) {
            if (isCanceled) {
                return;
            }
            if (currentValue <= 5) {
                subscriber.onNext(currentValue++);
            } else {
                subscriber.onComplete();
                return;
            }
        }
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }
}

class SimpleSubscriber implements Subscriber<Integer> {
    private Subscription subscription;
    private int itemsProcessed = 0;

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        // Request the first 2 items
        s.request(2);
    }

    @Override
    public void onNext(Integer integer) {
        System.out.println("Received: " + integer);
        itemsProcessed++;

        // Once we've processed 2 items, request 2 more
        if (itemsProcessed % 2 == 0) {
            subscription.request(2);
        }
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error: " + t.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("All items processed.");
    }
}

