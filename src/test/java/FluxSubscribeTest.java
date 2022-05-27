import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxSubscribeTest {
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
}
