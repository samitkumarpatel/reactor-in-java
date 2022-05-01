import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoService {

    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                //add log() to see more
                .log();
    }

    //Operator
    public Flux<String> fruitsFluxMap() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .map(String::toUpperCase);
    }

    public Flux<String> fruitsFluxFilter(int number) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .filter(s -> s.length() > number);
    }

    public Flux<String> fruitsFluxFilterMap(int number) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .filter(s -> s.length() > number)
                .map(String::toUpperCase);
    }

    public Flux<String> fruitsFluxFlatMap() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .log();
    }
    public Flux<String> fruitsFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .delayElements(
                        Duration.ofMillis(new Random().nextInt(1000))
                )
                .log();
    }

    public Flux<String> fruitsFluxConcatMap() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .delayElements(
                        Duration.ofMillis(new Random().nextInt(1000))
                )
                .log();
    }

    //TODO TO be continue on transform operator [58:58, https://www.youtube.com/watch?v=O26jhgk682Q&t=1515s]

    //mono
    public Mono<String> fruitMono() {
        return Mono.just("Mango");
    }

    public Mono<List<String>> fruitMonoFlatMap() {
        return Mono.just("Mango")
                .flatMap(s -> Mono.just(List.of(s.split(""))));
    }

    public Flux<String> fruitMonoFlatMapMany() {
        return Mono.just("Mango")
                .flatMapMany(s -> Flux.just(s.split("")));
    }

    public static void main(String[] args) {
        FluxAndMonoService fluxAndMonoService = new FluxAndMonoService();
        fluxAndMonoService.fruitsFlux()
                .subscribe(s -> System.out.println("S = %s".formatted(s)));

        fluxAndMonoService.fruitMono()
                .subscribe(s -> System.out.println("Mono s = %s".formatted(s)));
    }
}
