package vttp2022.ssf.assessment.videosearch.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

import vttp2022.ssf.assessment.videosearch.models.Game;
import vttp2022.ssf.assessment.videosearch.service.SearchService;
import vttp2022.ssf.assessment.videosearch.utils.JSONListConverter;

import java.util.List;

// import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(path = {"/search" })
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(produces = {"text/html"})
    public String getSearch(@RequestParam(required = true) String searchName, @RequestParam(required = false, defaultValue="10") int results, Model model) throws Exception {
        List<Game> gameList = searchService.search(searchName, results);
        model.addAttribute("size", gameList.size());
        model.addAttribute("gameList", gameList);
        return "search";
    }
}
