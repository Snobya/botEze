package vladp;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


//


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
//
import  org.telegram.telegrambots.bots.TelegramLongPollingBot;
import  org.telegram.telegrambots.bots.TelegramWebhookBot;
 import org.telegram.telegrambots.TelegramBotsApi;

/**
 * Hello world!
 */
public class App {


    public static String urleze;


    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");


        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new PictureRepeater());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

/*
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxbw1jfhm",
                "api_key", "572237234116541",
                "api_secret", "5_zyjCRIenPkUGfAoGnsFEyelss"));

       // File toUpload = new File("C:\\Users\\Владосик\\Desktop\\HHVkYgDoD28.jpg");
       // Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());


      //  String url = cloudinary.url().format("jpg")
      //          .transformation(new Transformation().width(250).height(168).crop("fit"))
      //          .generate("C:\\Users\\Владосик\\Desktop\\HHVkYgDoD28");
//http://res.cloudinary.com/dxbw1jfhm/image/upload/c_fit,h_168,w_250/sample.jpg

/*
/* Работает
        Map result = cloudinary.uploader().upload(new File("C:\\Users\\Владосик\\Desktop\\2.jpg"), ObjectUtils.asMap(
                "public_id", "samplee_id",
                "transformation", new Transformation().crop("limit").width(40).height(40),
                "eager", Arrays.asList(
                        new Transformation().width(200).height(200)
                                .crop("thumb").gravity("face").radius(20)
                                .effect("sepia"),
                        new Transformation().width(100).height(150)
                                .crop("fit").fetchFormat("png")
                ),
                "tags", "special, for_homepage"));
*/
/*
        File file = new File("C:\\Users\\Владосик\\Desktop\\1.jpg");



        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

        String publicId = (String) uploadResult.get("public_id"); //Получить айди картинки    http://res.cloudinary.com/demo/image/upload/tquyfignx5bxcbsupr6a.jpg

        String  tagWithUrl =  cloudinary.url().transformation(new Transformation().width(100).height(100).radius(10).effect("sepia").crop("crop")).imageTag(publicId+".jpg");

        System.out.println(tagWithUrl);


       int numb =  tagWithUrl.indexOf( '\'', 10);
        System.out.println(numb);

           urleze = tagWithUrl.substring(tagWithUrl.indexOf('\'')+1, numb);
        System.out.println(urleze);


*/

    }
}

