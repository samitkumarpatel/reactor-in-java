import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class SimpleFluxAndMono {
    public static void main(String[] args) {

        //Simple ways to create a Flux or Mono and subscribe to it

        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);

        Mono<String> noData = Mono.empty();
        Mono<String> data = Mono.just("foo");
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
/*
        subscribe();

        subscribe(Consumer<? super T> consumer);

        subscribe(Consumer<? super T> consumer,
                Consumer<? super Throwable> errorConsumer);

        subscribe(Consumer<? super T> consumer,
                Consumer<? super Throwable> errorConsumer,
                Runnable completeConsumer);

        subscribe(Consumer<? super T> consumer,
                Consumer<? super Throwable> errorConsumer,
                Runnable completeConsumer,
                Consumer<? super Subscription> subscriptionConsumer);
*/

        Flux<Integer> ints = Flux.range(1, 3);

        // Just subscribing doing nothing
        ints.subscribe();

        // Subscribing and printing
        ints.subscribe(i -> System.out.println(i));

        //subscribing , capturing success and error and dealing with that
        Flux<Integer> ints1 = Flux.range(1, 4).map(i -> {
            if (i <= 3) return i;
            throw new RuntimeException("Got to 4");
        });
        ints1.subscribe( i -> System.out.println(i), error -> System.err.println(error));

        //subscribing , success , error completion task
        Flux.range(1, 4).subscribe(
                i -> System.out.println(i),
                error -> System.err.println(error),
                () -> System.out.println("Done"));



    }
}
