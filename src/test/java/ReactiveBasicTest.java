import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Person {
    private long id;
    private String name;
    private float salary;
    private List<Integer> contacts;
}
public class ReactiveBasicTest {
    @Test
    void basicTest(){
        Flux.just(
                Person.builder().id(1l).name("Raju").salary(1000.0f).contacts(Arrays.asList(1,2,3)).build(),
                Person.builder().id(2l).name("Biju").salary(2540.0f).contacts(Arrays.asList(6,8)).build(),
                Person.builder().id(3l).name("Disco").salary(4700.0f).contacts(Arrays.asList(12,4)).build()
        )
                .map(person -> addBonus(person))
                .map(person -> formatName(person))
                .map(person -> assignId(person))
                .subscribe(
                        r -> System.out.println(r),
                        e -> System.out.println(e),
                        () -> System.out.println("Done")
                );
    }

    private Person assignId(Person person) {
        person.setId(System.currentTimeMillis());
        return person;
    }

    private Person formatName(Person person) {
        String name = person.getName();
        person.setName(name.toUpperCase());
        return person;
    }

    private Person addBonus(Person person) {
        float currentSalary = person.getSalary();
        float bonus = (currentSalary * 10)/100;
        person.setSalary(currentSalary + bonus);
        return person;
    }
}
