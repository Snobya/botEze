package vladp;

import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static vladp.App.urleze;

import com.cloudinary.*;


/**
 * Created by Andrew on 18-Sep-16.
 */
public class PictureRepeater extends TelegramLongPollingBot {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dxbw1jfhm",
            "api_key", "572237234116541",
            "api_secret", "5_zyjCRIenPkUGfAoGnsFEyelss"));

    String a;
    int w;
    int h;
    int r;
    String e = "";
    String chatId;

    public void onUpdateReceived(Update update) {
        try {

            if (update.hasMessage()) {
                Message message = update.getMessage();
                chatId = message.getChatId().toString();

                if (message.getPhoto() != null) {
                    List<PhotoSize> photos = message.getPhoto();
                    forward(photos.get(photos.size() - 1), chatId);

                }

                if (message.getText() != null) {

                    e = "";
                    a = message.getText();

                    if (a == "/start") {

                        sendN(chatId);

                    }

                    String local1 = "";
                    String local2 = "";
                    String local3 = "";
                    int counter = 0;

                    for (int i = 0; i < a.length(); i++) {


                        if (a.charAt(i) != '-') {
                            if (a.charAt(i) != ' ') {
                                if (counter == 0) {
                                    local1 += a.charAt(i);


                                }
                                if (counter == 1) {
                                    local2 += a.charAt(i);
                                }
                                if (counter == 2) {
                                    local3 += a.charAt(i);
                                }


                                if (counter == 3) {

                                    e += a.charAt(i);
                                }

                            } else {
                                counter++;

                            }
                        }
                    }
                    w = Integer.parseInt(local1);
                    System.out.println(w);
                    h = Integer.parseInt(local2);
                    System.out.println(h);
                    r = Integer.parseInt(local3);
                    System.out.println(r);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (NumberFormatException ex) { // handle your exception
            try {
                sendN(chatId);
            } catch (TelegramApiException e1) {
                e1.printStackTrace();
            }
        }
    }


    public PhotoSize getPhoto(Update update) {
        // Check that the update contains a message and the message has a photo
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // When receiving a photo, you usually get different sizes of it
            List<PhotoSize> photos = update.getMessage().getPhoto();

            // We fetch the bigger photo
            return photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null);
        }


        return null;
    }


    public String getFilePath(PhotoSize photo) {
        Objects.requireNonNull(photo);

        if (photo.hasFilePath()) { // If the file_path is already present, we are done!
            return photo.getFilePath();
        } else { // If not, let find it
            // We create a GetFile method and set the file_id from the photo
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                // We execute the method using AbsSender::getFile method.
                File file = getFile(getFileMethod);
                // We now have the file_path
                return file.getFilePath();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null; // Just in case
    }

    public java.io.File downloadPhotoByFilePath(String filePath) {
        try {
            // Download the file calling AbsSender::downloadFile method
            return downloadFile(filePath);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    private URLConnection getConnection(String filePath) {
        URLConnection conn = null;
        try {
            URL url = new URL("https://api.telegram.org/file/bot"
                    + getBotToken() + "/" + filePath);
            try {
                conn = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.out.println("That wasn't meant to happen.");
        }
        return conn;
    }


    private void forward(PhotoSize photo, String chatId) throws IOException, TelegramApiException {

        System.out.println(a);



        String path = this.getFilePath(photo);
        System.out.println(path);
        String url = "https://api.telegram.org/file/bot305190289:AAG0uI-FCqyfRQmAOj7WqLzQMN52J-B2EKE/" + path;


        URLConnection connection = new URL(url).openConnection();
        InputStream response = connection.getInputStream();

        Map uploadResult = cloudinary.uploader().upload(url, ObjectUtils.emptyMap()); //Вот тут загрузка файла


        String publicId = (String) uploadResult.get("public_id"); //Получить айди картинки    http://res.cloudinary.com/demo/image/upload/tquyfignx5bxcbsupr6a.jpg


        String tagWithUrl = cloudinary.url().transformation(new Transformation().width(w).height(h).radius(r).effect(e).crop("fill")).imageTag(publicId + ".jpg");


        System.out.println(tagWithUrl);


        int numb = tagWithUrl.indexOf('\'', 10);
        System.out.println(numb);

        urleze = tagWithUrl.substring(tagWithUrl.indexOf('\'') + 1, numb);
        System.out.println(urleze);



        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chatId)
                .setText("Your url link: " + urleze);
        sendMessage(message);

    }

    void sendN(String chatId) throws TelegramApiException {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chatId)
                .setText("An easy way to edit your picture.\n" +
                        "Just set a height, a width, a radius and an effect in format. For exemple,\" 200 200 10 sepia\" and send a photo.\n"+"e:sepia, pixelate_faces, saturation:70, gradient_fade, brightness:50, green:-50"
                        );
        sendMessage(message);
    }

    public String getBotUsername() {
        return BotInfo.BOT_USERNAME;
    }


    public String getBotToken() {
        return BotInfo.BOT_TOKEN;

    }

}

