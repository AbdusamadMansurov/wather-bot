import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import model.locationService.Service;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import wather.ResponseWather;
import wather.Wather;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WatherBotServiceImpl implements WatherBotService{
    static Wather wather = new Wather();
    static String address;
    @Override
    public SendMessage welcome(Update update, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(Constatnt.WELCOME + "\nLocatsiyangizni jo'nating");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setRequestLocation(true);
        button.setText("Share Location");
        row.add(button);
        rows.add(row);
        replyKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage getAddress(Update update, String chatId) throws IOException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Location location = update.getMessage().getLocation();
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
         address = Service.getAddress(location.getLatitude(), location.getLongitude());
        sendMessage.setText("Sizning manzilingiz: " + address);
        OkHttpClient client = new OkHttpClient();
        String shahar = address.substring(0, address.indexOf(","));
        Request request = new Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/weather?q=" + shahar + "&lat=" + latitude +"&lon="+ longitude + "&callback=Mytest&id=2172797&lang=null&units=metric&mode=json")
                .get()
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "6d028eb1d6msh2d83ac103948764p11a0fejsn04cda172f4b8")
                .build();
        Response response = client.newCall(request).execute();
        String mess = "";

        try {
            mess = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file =new File("src/main/resources/wathers.json");
        boolean newFile = file.createNewFile();
        System.out.println(newFile);
        Gson gson = new Gson();
        mess = mess.substring(7,mess.length()-1);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter ("src/main/resources/wathers.json"));
        bufferedWriter.write(mess);
        bufferedWriter.close();
        wather = gson.fromJson(
                new BufferedReader(new FileReader("src/main/resources/wathers.json")),
                new TypeToken<Wather>() {
                }.getType());
        String message = response.message();
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        return sendMessage;
    }

    @Override
    public SendMessage getWather(Update update, String chatID) throws IOException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);

        HttpGet get = new HttpGet("https://api.openweathermap.org/data/2.5/weather?q=Tashkent&cnt=7&appid=cc36e60e0384107b7c7513fd23ef0c7f");
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(get);
        System.out.println(response.getEntity().getContent());
        Reader reader = new InputStreamReader(response.getEntity().getContent());
        Gson gson = new Gson();

        ResponseWather watherResponse = gson.fromJson(reader, new TypeToken<ResponseWather>() {
        }.getType());
//       System.out.println(watherResponse);

        StringBuilder watherText = new StringBuilder("Joriy temeratura: " + (wather.getMain().getTemp()));
        watherText.append("C\nJoriy minimal temeratura: " + (wather.getMain().getTempMin()));
        watherText.append("C\nJoriy maximal temeratura: " + (wather.getMain().getTempMax()));
        watherText.append("C\nJoriy atmosfera bosmi: " + (wather.getMain().getPressure() + "hPa"));
        watherText.append("\nJoriy havo namligi: " + (wather.getMain().getHumidity() + "%"));
        watherText.append("\nJoriy shamol tezligi: " + wather.getWind().getSpeed());
        watherText.append("m/s\nJoriy ob-havo sharoiti: " + wather.getWeather().get(0).getDescription());
        watherText.append(watherResponse.getWeather());
        sendMessage.setText("Sizning manzilingiz: " + address + "\n" + watherText);

        return sendMessage ;
    }
}
