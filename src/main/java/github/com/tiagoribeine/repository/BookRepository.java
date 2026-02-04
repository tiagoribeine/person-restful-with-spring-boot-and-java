package github.com.tiagoribeine.repository;

import github.com.tiagoribeine.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    /* <Entidade que esse repository gerencia, tipo do Id da Entidade>
     Só isso é o suficiente para executar um CRUD
     JpaRepository representa um "contrato" com metodos CRUD básicos*/

}
