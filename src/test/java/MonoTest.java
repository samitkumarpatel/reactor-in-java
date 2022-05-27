import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {
    @Test
    void empty() {
        Mono<String> noData = Mono.empty();
        StepVerifier.create(noData).expectNext().verifyComplete();

        Mono<String> data = Mono.just("foo");
        StepVerifier.create(data).expectNext("foo").verifyComplete();
    }
}
