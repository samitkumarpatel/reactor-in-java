package mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MonoTest {
    @Test
    void monoEmpty() {
        Mono<String> emptyMonoString = Mono.empty();
        assertNotNull(emptyMonoString);
        StepVerifier.create(emptyMonoString).expectNextCount(0).verifyComplete();
        // If emptyMonoString emit any data it will expect onNext, Hence below will fail
        StepVerifier.create(emptyMonoString).verifyComplete();
    }

    @Test
    void monoError() {
        Mono<String> errorMono = Mono.error(new RuntimeException("String based operation error"));
        assertNotNull(errorMono);
        StepVerifier.create(errorMono).expectError().verify();
        StepVerifier.create(errorMono).expectError(RuntimeException.class).verify();
    }

    @Test
    void monoFromSupplier() {
        //Mono.just(getName()); // Not a good practice . With this approach getName method invoked - as this is against reactive
        Mono<String> monoString = Mono.fromSupplier(() -> getName());
        StepVerifier.create(monoString).expectNext("Samit").verifyComplete();
    }
    private String getName() {
        System.out.println("getName method invoked");
        return "Samit";
    }

    @Test
    void monoFromCallable() {
        Mono<String> monoString = Mono.fromCallable(() -> getName());
        StepVerifier.create(monoString).expectNext("Samit").verifyComplete();
    }
}
