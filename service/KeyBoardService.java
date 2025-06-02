package tag.sources.checkmebot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyBoardService {
    public ReplyKeyboardMarkup getTextKeyBoard() {
        return getStartKeyBoard();
    }

    public ReplyKeyboardMarkup getSchedulerKeyBoard() {
        ReplyKeyboardMarkup keyboardMarkup = getStartKeyBoard();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("\uD83C\uDD95 Расписание. 10 секунд"));
        row1.add(new KeyboardButton("\uD83C\uDD95 Расписание. 1 минута"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("\uD83C\uDD95 Расписание. 1 час"));
        row2.add(new KeyboardButton("\uD83C\uDD95 Расписание. 5 часов"));
        keyboardMarkup.getKeyboard().add(row1);
        keyboardMarkup.getKeyboard().add(row2);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getBusinessKeyBoard() {
        ReplyKeyboardMarkup keyboardMarkup = getStartKeyBoard();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("\uD83C\uDD95 Настроить задачу по расписанию"));
        keyboardMarkup.getKeyboard().add(row1);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getUserKeyBoard() {
        ReplyKeyboardMarkup keyboardMarkup = getStartKeyBoard();
        //KeyboardRow row1 = new KeyboardRow();
        //row1.add(new KeyboardButton("\uD83C\uDD95 Создать опрос"));
        //keyboardMarkup.getKeyboard().add(row1);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getFilesKeyBoard() {
        return getStartKeyBoard();
    }

    public ReplyKeyboardMarkup getInfoKeyBoard() {
        return getStartKeyBoard();
    }

    public ReplyKeyboardMarkup getStartKeyBoard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("\uD83D\uDCC4 Текстовый бот"));
        row1.add(new KeyboardButton("\uD83C\uDFE2 Для бизнеса и личного блога"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("\uD83D\uDCE6 Работа с файлами"));
        row2.add(new KeyboardButton("\uD83D\uDC65 Взаимодействие с пользователями"));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("ℹ️ Сотрудничество"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
    }
}
