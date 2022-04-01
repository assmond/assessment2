package vttp2022.ssf.assessment.videosearch.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp2022.ssf.assessment.videosearch.models.Game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    private static final String API_SERVICE = "https://api.rawg.io/api/";

    @Value("${rawg.api.key}")
    private String apiKey;

    private Game createGameObject(String name, String backgroundImage, Float rating){
        Game game = new Game();
        game.setName(name);
        game.setBackgroundImage(backgroundImage);
        game.setRating(rating);
        return game;
    }

    public List<Game> search(String searchString, Integer count){
        String apiUrl = API_SERVICE + "games";
        String url = UriComponentsBuilder.fromUriString(apiUrl).queryParam("key",apiKey).queryParam("search",searchString).queryParam("page_size",count).toUriString();
        RestTemplate template = new RestTemplate();
        RequestEntity request = RequestEntity.get(url).build();
        List<Game> gamesList = new ArrayList<Game>();

        try{
            ResponseEntity<String> response = template.exchange(request,String.class);
            if (response.getStatusCodeValue() == 200){
                String jsonBody = response.getBody();
                JsonObject respJson = Json.createReader(new StringReader(jsonBody)).readObject();
                JsonArray jsonArray = respJson.getJsonArray("results");
                for (int i =0; i<jsonArray.size(); i++){
                    JsonObject jsonObject = (JsonObject)jsonArray.get(i);
                    Game game = createGameObject(jsonObject.get("name").toString(),jsonObject.get("background_image").toString(), Float.valueOf(jsonObject.get("rating").toString()).floatValue());
                    gamesList.add(game);
                }
            }
            else {
                logger.info("Did not receive expected response of status 200. Got response of " + response.getStatusCodeValue());
            }
        } catch (RestClientException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalStateException e) {
            logger.error(e.getMessage(), e);
        }

        return gamesList;

    }

}
