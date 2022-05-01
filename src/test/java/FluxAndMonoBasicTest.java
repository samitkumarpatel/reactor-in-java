import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FluxAndMonoBasicTest {
    @BeforeAll
    static void createDatabase() {

    }

    @BeforeEach
    void connectToDatabase() {

    }

    @AfterEach
    void disconnectFromDatabase() {

    }

    @AfterAll
    static void destroyDatabase() {

    }

    @Test
    @DisplayName("zipWith Example")
    void testOne() {
        Flux<Integer> l1 = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> l2 = Flux.just(11, 12, 13, 14, 15);

        var val = l1.zipWith(l2).collect(Collectors.toList());

        StepVerifier.create(val)
                .expectNextCount(1)
                .verifyComplete();

        assertAll("Hello",
                () -> assertEquals(1, 1),
                () -> assertEquals("H", "H")
        );
    }
}
