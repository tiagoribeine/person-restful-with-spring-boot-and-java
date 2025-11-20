package github.com.tiagoribeine;

import github.com.tiagoribeine.model.Person;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

// Aqui teremos Operações para cadastrar uma pessoa

@Service //Marca a classe como Serviço gerenciado pelo Spring. Faz com que fique disponível e seja injetado onde necessário
public class PersonServices {

    private final AtomicLong counter = new AtomicLong(); // Gera IDs únicos thread-safe
    private Logger logger = Logger.getLogger(PersonServices.class.getName()); //Sistema de logging para monitorar o que acontece no serviço

    public List<Person> findAll(){
        logger.info("Finding all people!");
        List<Person> persons = new ArrayList<Person>();
        for(int i = 0; i <8; i++){
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    public Person create(Person person){
        logger.info("Creating one Person!");
        //Caso estivessemos conectados ao banco de dados, faríamos o acesso aqui
        return person;
    }

    public Person update(Person person){
        logger.info("Updating one Person!");
        //Caso estivessemos conectados ao banco de dados, faríamos o acesso aqui
        return person;
    }

    public void delete(String id) {
        //Não retorna conteúdo
        logger.info("Deleting one person!");

    }




    public Person findById(String id) {
        logger.info("Finding one Person");

        Person person = new Person();
        person.setId(counter.incrementAndGet()); // Id automático
        person.setFirstName("Tiago");
        person.setLastName("Ribeiro");
        person.setAddress("Sao Paulo - Sao Paulo - Brazil");
        person.setGender("Male");
        return person;
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet()); // Id automático
        person.setFirstName("FirstName " + i);
        person.setLastName("LastName " + i);
        person.setAddress("Some Address in Brazil " + i);
        person.setGender("Male");
        return person;
    };
}