import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FluxTest {
    @Test
    void create() {

    }

    @Test
    void just() {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        StepVerifier
                .create(seq1)
                .expectNext("foo", "bar", "foobar")
                .verifyComplete();

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);
        StepVerifier
                .create(seq2)
                .expectNext("foo", "bar", "foobar")
                .verifyComplete();
    }

    @Test
    void range() {
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
        StepVerifier.create(numbersFromFiveToSeven).expectNext(5,6,7).verifyComplete();
    }

    @Test
    void subscribe() {
        Flux<Integer> ints = Flux.range(1, 3);
        //1) Output:
        ints.subscribe();

        //2) Output: 1,2,3
        ints.subscribe(
                i -> System.out.println(i)
        );

        //3) Output: 1,2,3,Error: java.lang.RuntimeException: Got to 4
        Flux<Integer> ints1 = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints1.subscribe(
                i -> System.out.println(i),
                error -> System.err.println("Error: " + error)
        );

        //4) Output: 1,2,3,4,Done
        Flux<Integer> ints2 = Flux.range(1, 4);
        ints2.subscribe(
                i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done")
        );

        //5) Output: 1,2,3,4,Done
        Flux<Integer> ints3 = Flux.range(1, 4);
        ints3.subscribe(
                i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(10)
        );
    }

    @Test
    void generate() {
        //1)
        Flux<String> flux0 = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x %s = %s".formatted(state,3*state));
                    if (state == 10)
                        sink.complete();
                    return state + 1;
                });
        flux0.subscribe(System.out::println);

        //2)
        Flux<String> flux1 = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x %s = %s".formatted(i, 3*1));
                    if (i == 10) sink.complete();
                    return state;
                });
        flux1.subscribe(System.out::println);

        //3)
        Flux<String> flux2 = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x %s = %s".formatted(i, 3*1));
                    if (i == 10) sink.complete();
                    return state;
                },
                (state) -> System.out.println("state: " + state));
        flux2.subscribe();
    }

    @Test
    void publishOn() {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i)
                .publishOn(s)
                .map(i -> "value " + i);

        new Thread(() -> flux.subscribe(System.out::println)).run();
    }

    @Test
    void subscribeOn() {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i)
                .subscribeOn(s)
                .map(i -> "value " + i);

        new Thread(() -> flux.subscribe(System.out::println)).run();
    }

}
