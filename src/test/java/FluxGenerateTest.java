import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

public class FluxGenerateTest {

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
}
