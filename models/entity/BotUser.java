package tag.sources.checkmebot.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bot_user")
public class BotUser {
    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "username")
    private String username;
}
