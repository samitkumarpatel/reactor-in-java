import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class OnErrorReturnTest {

    private <T> String doSomethingDangerous(Integer integer) {
        if(integer >= 10) throw new RuntimeException(" is grater than 10");
        return "integer = %s".formatted(integer);
    }
    private <T> String doSomethingDangerousOne(Integer integer) {
        if(integer >= 10) throw new RuntimeException("boom10");
        return "integer = %s".formatted(integer);
    }

    @Test
    void onErrorReturn() {
        //1)
        Flux r0 = Flux.just(1, 2, 0)
                .map(i -> "100 / %s = %s".formatted(1,(100 / i)) ) //this triggers an error with 0
                .onErrorReturn("Divided by zero :(");// error handling example
        StepVerifier
                .create(r0)
                .expectNext("100 / 1 = 100","100 / 1 = 50","Divided by zero :(")
                .verifyComplete();

        //2) mapping and onError return a fixed value
        Flux r1 = Flux.just(10)
                .map(this::doSomethingDangerous)
                .onErrorReturn("RECOVERED");
        StepVerifier
                .create(r1)
                .expectNext("RECOVERED")
                .verifyComplete();

        //3) mapping , onError and a fallback value
        Flux r2 =Flux.just(10)
                .map(this::doSomethingDangerousOne)
                .onErrorReturn(e -> e.getMessage().equals("boom10"), "recovered10");
        StepVerifier
                .create(r2)
                .expectNext("recovered10")
                .verifyComplete();

    }
}
