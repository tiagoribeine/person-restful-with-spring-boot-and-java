package github.com.tiagoribeine.repository;

import github.com.tiagoribeine.integrationtests.testcontainers.AbstractIntegrationTest;
import github.com.tiagoribeine.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //Integra o Spring Framework com o Junit 5 - Instrui o Unit para carregar o contexto do Spring
@DataJpaTest //Configura o Teste para trabalhar com JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Garante que o banco de dados real(MySQL, Postgres etc) será configurado na aplicação e será utilizado nos testes
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Define a ordem de execução dos testes
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired //Adicionando a injeção do repositório
    PersonRepository repository;
    private static Person person;

    @BeforeAll
    static void setUp() {
        person = new Person();
    }

    @Test
    @Order(1)
    void findPeopleByName() {
        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "firstName"));

        person = repository.findPeopleByName("iko", pageable).getContent().get(0);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Nikola", person.getFirstName());
        assertEquals("Tesla", person.getLastName());
        //assertEquals("Smiljan - Croatia", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person.getEnabled());
    }

    @Test
    @Order(2)
    void disablePerson() {

        Long id = person.getId();
        repository.disablePerson(id);

        var result = repository.findById(id);
        person = result.get();

        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Nikola", person.getFirstName());
        assertEquals("Tesla", person.getLastName());
        //assertEquals("Smiljan - Croatia", person.getAddress());
        assertEquals("Male", person.getGender());
        assertFalse(person.getEnabled());
    }
}