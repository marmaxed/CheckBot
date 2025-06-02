package tag.sources.checkmebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tag.sources.checkmebot.models.entity.BotUser;

@Repository
public interface UserRepository extends JpaRepository<BotUser, Long> {
}
