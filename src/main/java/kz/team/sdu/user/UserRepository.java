package kz.team.sdu.user;

import kz.team.sdu.test.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
