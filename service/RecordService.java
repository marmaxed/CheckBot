package tag.sources.checkmebot.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import tag.sources.checkmebot.CheckBot;
import tag.sources.checkmebot.models.entity.BotUser;
import tag.sources.checkmebot.repository.UserRepository;
import tag.sources.checkmebot.utils.TextConsts;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordService {
    private final UserRepository userRepo;
    private final KeyBoardService keyBoardService;
    private final ScheduleTaskService scheduleTaskService;

    public ImmutablePair<String, ReplyKeyboardMarkup> handleMessage(Long chatId, String text, CheckBot checkBot) {
        switch (text) {
            case "📄 Текстовый бот" -> {
                ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getTextKeyBoard();
                return new ImmutablePair(TextConsts.TEXT_BOT_MESSAGE, replyKeyboardMarkup);
            }
            case "\uD83C\uDFE2 Для бизнеса и личного блога" -> {
                ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getBusinessKeyBoard();
                return new ImmutablePair(TextConsts.BUSINESS_BOT_MESSAGE, replyKeyboardMarkup);
            }
            case "\uD83D\uDC65 Взаимодействие с пользователями" -> {
                ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getUserKeyBoard();
                return new ImmutablePair(TextConsts.USER_BOT_MESSAGE, replyKeyboardMarkup);
            }
            case "\uD83D\uDCE6 Работа с файлами" -> {
                ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getFilesKeyBoard();
                return new ImmutablePair(TextConsts.FILES_BOT_MESSAGE, replyKeyboardMarkup);
            }
            case "\uD83C\uDD95 Настроить задачу по расписанию" -> {
                ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getSchedulerKeyBoard();
                return new ImmutablePair(TextConsts.SCHEDULER_BOT_MESSAGE, replyKeyboardMarkup);
            }
            case "ℹ️ Сотрудничество" -> {
                ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getInfoKeyBoard();
                return new ImmutablePair(TextConsts.INFO_BOT_MESSAGE, replyKeyboardMarkup);
            }
            case "/show" -> {
                return new ImmutablePair("Простая команда", keyBoardService.getStartKeyBoard());
            }
            default -> {
                if (text.contains("Расписание")) {
                    return scheduleTaskService.createTask(checkBot, chatId, text);
                }
                registerUser(chatId);
                ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getStartKeyBoard();
                return new ImmutablePair(TextConsts.START_BOT_MESSAGE, replyKeyboardMarkup);
            }
        }
    }

    public ImmutablePair<Document, ReplyKeyboardMarkup> handleMessage(Long chatId, Document file, CheckBot checkBot) {
        ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getTextKeyBoard();
        return new ImmutablePair(TextConsts.TEXT_BOT_MESSAGE, replyKeyboardMarkup);
    }

    private String registerUser(Long chatId) {
        if (userRepo.existsById(chatId)) {
            return "С возвращением";
        }
        userRepo.save(new BotUser(chatId, "BotUser" + chatId));
        return "Привет, я простой бот с базовым функционалом, сделанный, чтоб продемонстрировать свою работу. \n"
                + "А вот ссылка на разработчика, который меня сделал:\n";
    }
}
