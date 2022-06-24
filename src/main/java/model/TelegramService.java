package model;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramService {
    SendMessage wel(Update update, String chatId);

    SendMessage menu(Update update, String chatId);

    SendMessage order(Update update, String chatId);

    SendMessage location(Update update, String chatId);

    SendMessage shareContact(Update update, String chatId);

}
