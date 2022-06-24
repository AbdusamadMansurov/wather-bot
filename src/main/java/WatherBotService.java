import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface WatherBotService {
    SendMessage welcome (Update update, String chatId );

    SendMessage getAddress(Update update, String chatId) throws IOException;
    SendMessage getWather(Update update, String chatID) throws IOException;

}
