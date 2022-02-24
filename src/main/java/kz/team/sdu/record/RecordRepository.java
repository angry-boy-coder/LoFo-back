package kz.team.sdu.record;

import kz.team.sdu.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {

}
