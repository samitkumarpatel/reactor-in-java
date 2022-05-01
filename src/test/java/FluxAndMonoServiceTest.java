import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class FluxAndMonoServiceTest {

    FluxAndMonoService fluxAndMonoService = new FluxAndMonoService();

    // we will not be able to use FLux and Mono with traditional way, Hence we have to use Project Reactor Test module

    @Test
    void fruitsFlux() {
        var fruitsFlux = fluxAndMonoService.fruitsFlux();
        StepVerifier.create(fruitsFlux)
                .expectNext("Mango", "Orange", "Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMap() {
        var fruitsFluxMap = fluxAndMonoService.fruitsFluxMap();
        StepVerifier.create(fruitsFluxMap)
                .expectNext("MANGO", "ORANGE", "BANANA")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilter() {
        var fruitsFluxFilter = fluxAndMonoService.fruitsFluxFilter(5);
        StepVerifier.create(fruitsFluxFilter)
                .expectNext("Orange", "Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilterMap() {
        var fruitsFluxFilterMap = fluxAndMonoService.fruitsFluxFilterMap(5);
        StepVerifier.create(fruitsFluxFilterMap)
                .expectNext("ORANGE", "BANANA")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMap() {
        var fruitsFluxFlatMap = fluxAndMonoService.fruitsFluxFlatMap();
        StepVerifier.create(fruitsFluxFlatMap)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMapAsync() {
        var fruitsFluxFlatMapAsync = fluxAndMonoService.fruitsFluxFlatMapAsync();
        StepVerifier.create(fruitsFluxFlatMapAsync)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatMap() {
        var fruitsFluxConcatMap = fluxAndMonoService.fruitsFluxConcatMap();
        StepVerifier.create(fruitsFluxConcatMap)
                .expectNextCount(17)
                .verifyComplete();
    }

    //Mono
    @Test
    void fruitMono() {
        var fruitMono = fluxAndMonoService.fruitMono();
        StepVerifier.create(fruitMono)
                .expectNext("Mango")
                .verifyComplete();
    }

    @Test
    void fruitMonoFlatMap() {
        var fruitMonoFlatMap = fluxAndMonoService.fruitMonoFlatMap();
        StepVerifier.create(fruitMonoFlatMap)
                .expectNextCount(1)
                .verifyComplete();
    }


    @Test
    void fruitMonoFlatMapMany() {
        var fruitMonoFlatMapMany = fluxAndMonoService.fruitMonoFlatMapMany();
        StepVerifier.create(fruitMonoFlatMapMany)
                .expectNextCount(5)
                .verifyComplete();
    }
}
