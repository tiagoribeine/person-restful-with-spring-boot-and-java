package github.com.tiagoribeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}

    //Criar modelo que será retornado aos usuários(JSON com 2 atributos id e content)
}

