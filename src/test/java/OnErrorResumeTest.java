import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class OnErrorResumeTest {
    private Flux<String> callExternalService(String k) {
        throw new RuntimeException();
    }
    private Mono<String> getFromCache(String k) {
        return Mono.just("FromCache");
    }

    class BusinessException extends Exception {
        BusinessException(String msg) {
            super(msg);
        }
        BusinessException(String msg, Throwable t) {
            super(msg,t);
        }
    }
    @Test
    void onErrorResume() {

        Flux f1 = Flux.just("k1","k2")
                .flatMap(k -> callExternalService(k).onErrorResume(e -> getFromCache(k)));
        //TODO Fix me - it's not satisfactory
        StepVerifier.create(f1)
                .expectError()
                .verify();

        Flux.just("timeout1")
                .flatMap(k -> callExternalService(k))
                .onErrorResume(original -> Flux.error(
                        new BusinessException("oops, SLA exceeded", original))
                );
    }
}
