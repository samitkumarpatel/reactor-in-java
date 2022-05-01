import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;

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

        l1.zipWith(l2).subscribe(i -> System.out.println(i));

        assertAll("Hello",
                () -> assertEquals(1, 1),
                () -> assertEquals("H", "H")
        );
    }
}
