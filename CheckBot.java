package tag.sources.checkmebot;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import tag.sources.checkmebot.config.BotConfig;
import tag.sources.checkmebot.repository.UserRepository;
import tag.sources.checkmebot.service.RecordService;

@Component
@NoArgsConstructor
public class CheckBot extends TelegramLongPollingBot {
    private BotConfig botConfig;
    private UserRepository userRepository;
    private RecordService recordService;

    @Autowired
    public CheckBot(BotConfig botConfig, UserRepository userRepository, RecordService recordService) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.recordService = recordService;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    public void sendMessage(long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode("HTML");

        keyboardMarkup.setResizeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(long chatId, InputFile file, ReplyKeyboardMarkup keyboardMarkup) {
        SendDocument sendDocumentRequest = new SendDocument();
        sendDocumentRequest.setChatId(chatId);
        sendDocumentRequest.setDocument(file);

        keyboardMarkup.setResizeKeyboard(true);
        sendDocumentRequest.setReplyMarkup(keyboardMarkup);
        try {
            execute(sendDocumentRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            ImmutablePair<String, ReplyKeyboardMarkup> result = recordService.handleMessage(chatId, text, this);
            sendMessage(chatId, result.getLeft(), result.getRight());
        } else if (update.hasMessage() && update.getMessage().hasDocument()) {
            Document file = update.getMessage().getDocument();
            long chatId = update.getMessage().getChatId();
            ImmutablePair<Document, ReplyKeyboardMarkup> result = recordService.handleMessage(chatId, file, this);
            sendFile(chatId, result.getLeft(), result.getRight());
        }
    }
}
