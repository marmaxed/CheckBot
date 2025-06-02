package tag.sources.checkmebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tag.sources.checkmebot.models.entity.BotRecord;
import tag.sources.checkmebot.models.entity.BotUser;

import java.util.List;

@Repository
public interface BotRecordRepository extends JpaRepository<BotRecord, Long> {
    List<BotRecord> findByBotUser(BotUser botUser);
}
