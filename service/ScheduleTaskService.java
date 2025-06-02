package tag.sources.checkmebot.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import tag.sources.checkmebot.CheckBot;
import tag.sources.checkmebot.utils.TextConsts;

import java.util.Date;

@Service
public class ScheduleTaskService {
    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private KeyBoardService keyBoardService;

    public void scheduleTaskWithDelay(Runnable task, long delayInMillis) {
        Date startTime = new Date(System.currentTimeMillis() + delayInMillis);
        taskScheduler.schedule(task, startTime);
    }

    public ImmutablePair<String, ReplyKeyboardMarkup> createTask(CheckBot checkBot, Long chatId, String text) {
        long millis = switch (text) {
            case "\uD83C\uDD95 Расписание. 10 секунд" -> 10000L;
            case "\uD83C\uDD95 Расписание. 1 минута" -> 60000L;
            case "\uD83C\uDD95 Расписание. 1 час" -> 3600000L;
            case "\uD83C\uDD95 Расписание. 5 часов" -> 18000000L;
            default -> 1L;
        };
        scheduleTaskWithDelay(() -> checkBot.sendMessage(chatId,
                "Пришел ответ по времени вашего задания", keyBoardService.getStartKeyBoard()), millis);
        ReplyKeyboardMarkup replyKeyboardMarkup = keyBoardService.getStartKeyBoard();
        return new ImmutablePair(TextConsts.START_TASK_BOT_MESSAGE, replyKeyboardMarkup);
    }
}
