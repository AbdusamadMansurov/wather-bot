package model;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TelegramServiceImpl implements TelegramService{

        public SendMessage wel(Update update, String chatId) {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setText(Const.WELCOME);
        sendMessage.setChatId(chatId.toString());

        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> rows=new ArrayList<>();
        KeyboardRow row=new KeyboardRow();
        KeyboardButton buttonuz = new KeyboardButton(Const.BUTTON_UZ);
        KeyboardButton buttonru = new KeyboardButton(Const.BUTTON_RU);

        row.add(buttonuz);
        row.add(buttonru);
        rows.add(row);
        replyKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;

    }
    @Override
    public SendMessage menu(Update update, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Bosh menyu");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        if (update.getMessage().getText().equals(Const.BUTTON_UZ)) {
            List<KeyboardRow> rows = new ArrayList<>();
            KeyboardRow row0 = new KeyboardRow();
            KeyboardButton button0 = new KeyboardButton();
            button0.setText(Const.MENU_BUYURTMA);
            row0.add(button0);
            rows.add(row0);
            KeyboardRow row1 = new KeyboardRow();
            KeyboardButton button = new KeyboardButton();
            KeyboardButton button1 = new KeyboardButton();
            button.setText(Const.MENU_BUYURTMA_TARIX);
            button1.setText(Const.MENU_ALOQA);
            row1.add(button);
            row1.add(button1);
            rows.add(row1);
            KeyboardRow row2 = new KeyboardRow();
            KeyboardButton button2 = new KeyboardButton();
            button2.setText(Const.MENU_SOZLAMA);
            row2.add(button2);
            rows.add(row2);
            KeyboardRow row3 = new KeyboardRow();
            KeyboardButton button3 = new KeyboardButton();
            button3.setText(Const.MENU_JAMOAGA_QOSHISH);
            row3.add(button3);
            rows.add(row3);
            replyKeyboardMarkup.setKeyboard(rows);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
        } else {
            sendMessage.setText("asd");
            return sendMessage;
        }
    }
    @Override
    public SendMessage order(Update update, String chatId) {
      SendMessage sendMessage = new SendMessage();
      sendMessage.setText("Buyurtmani o'zingiz olib keting \nYoki Yetkazib berishni tanlang");
      sendMessage.setChatId(chatId);
      ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
      replyKeyboardMarkup.setResizeKeyboard(true);
      replyKeyboardMarkup.setOneTimeKeyboard(true);
      replyKeyboardMarkup.setSelective(true);
      KeyboardRow row = new KeyboardRow();
      KeyboardButton button = new KeyboardButton();
      KeyboardButton button1 = new KeyboardButton();
      button.setText(Const.BUTTON_DOSTAVKA);
      button1.setText(Const.BUTTON_OLIB_KETISH);
      row.add(button);
      row.add(button1);
      List<KeyboardRow> list = new ArrayList<>();
      list.add(row);
      replyKeyboardMarkup.setKeyboard(list);
      sendMessage.setReplyMarkup(replyKeyboardMarkup);
      return sendMessage;
        }

    @Override
    public SendMessage location(Update update, String chatId) {
        return null;
    }

    @Override
    public SendMessage shareContact(Update update, String chatId) {
        return null;
    }

}
