import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class WatherBot extends TelegramLongPollingBot{
    static int level = 0;
    static WatherBotService watherBotService = new WatherBotServiceImpl();
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new WatherBot());
    }

    @Override
    public String getBotUsername() {
        return Constatnt.NAME_BOT;
    }

    @Override
    public String getBotToken() {
        return Constatnt.TOKKEN_BOT;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
      String chatId = String.valueOf(update.getMessage().getChatId());
      Message message = update.getMessage();
      String text = " ";
      if(update.getMessage().hasText())
       text = message.getText();
      if(text.equals(Constatnt.START)){
          execute(watherBotService.welcome(update, chatId));
          level = 1;
      }else {
          switch (level){
              case 1 : execute(watherBotService.getAddress(update, chatId));
              execute(watherBotService.getWather(update, chatId));
              level = 2;
              break;
          }
      }
    }
}
