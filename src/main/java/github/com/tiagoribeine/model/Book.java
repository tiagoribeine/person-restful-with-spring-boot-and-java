package github.com.tiagoribeine.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "books")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L; //Número da versão para controle durante a serialização

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Como será gerado o Id
    private Long id; // Será fornecido pelo banco de dados

    @Column(nullable = false, length = 180) //Nome da Coluna na tabela do DB, Não pode ser nulo, tamanho máximo
    private String author;

    @Column(name = "launch_date", nullable = false) //Nome da Coluna na tabela do DB, Não pode ser nulo, tamanho máximo
    @Temporal(TemporalType.DATE)
    private Date launch_date;

    @Column(nullable = false) //Nome não especificado, Spring entende que o nome é igual na coluna da tabela(db) e na entidade
    private Double price;

    @Column(nullable = false, length = 250) //Nome não especificado, Spring entende que o nome é igual na coluna da tabela(db) e na entidade
    private String title;

    public Book() {} //Construtor - Spring Data JPA e frameworks de serialização exigem construtor vazio

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(Date launch_date) {
        this.launch_date = launch_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getLaunch_date(), book.getLaunch_date()) && Objects.equals(getPrice(), book.getPrice()) && Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
    }
}
