package github.com.tiagoribeine.repository;

import github.com.tiagoribeine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> { //<Tipo da Entidade, Tipo do ID da entidade>>

    @Query("SELECT u FROM User u WHERE u.userName =:userName ") //username deve ser passado igual esta na entidade User
    User findByUsername(@Param("userName") String userName);
}
