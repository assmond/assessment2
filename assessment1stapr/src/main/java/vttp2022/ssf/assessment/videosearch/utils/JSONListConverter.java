package vttp2022.ssf.assessment.videosearch.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
// import com.google.gson.JsonElement;
// import com.google.gson.JsonObject;
// import com.google.gson.JsonParser;

import vttp2022.ssf.assessment.videosearch.models.Game;

public class JSONListConverter {
    public static String convert(List<Game> gamesList) throws IOException
    {
        Gson gson = new GsonBuilder().create();
 
        //Define map which will be converted to JSON
        // List list = Stream.of(gamesList).collect(Collectors.toList());
        
        String jsonString = gson.toJson(Stream.of(gamesList).collect(Collectors.toList()));
        
        return jsonString;
    }
}
