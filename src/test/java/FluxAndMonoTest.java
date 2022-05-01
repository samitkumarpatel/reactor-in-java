import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FluxAndMonoTest {
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

        l1.zipWith(l2).subscribe(i -> {
            var t1 = i.getT1();
            var t2 = i.getT2();
            //System.out.println(i);
            System.out.println(t1);
            System.out.println(t2);




        });

        assertAll("Hello",
                () -> assertEquals(1, 1),
                () -> assertEquals("H", "H")
        );
    }
}
