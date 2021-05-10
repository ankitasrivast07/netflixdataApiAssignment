package com.movie.netflixdata.controller;

import com.movie.netflixdata.models.ContentModel;
import com.movie.netflixdata.repository.FetchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class FetchDataFromDb {

    @Autowired
    FetchData fetchData;

    @Autowired
    private FetchData netflixData;

    @PostMapping(path = "tvshows", consumes="application/json", params = "flag") //  flag= db or csv(define where data need to save)
    String saveNetflix(@RequestBody ContentModel model, @RequestParam String flag){
         if(flag=="db") {
             //saving in db
          netflixData.save(model);

         }else{
        // for saving in csv

         }
        return "";
    }

//Fetching data from database


    @GetMapping(path="/tvshows", params = "count", produces = "application/json")

    List<ContentModel> getNumData( @RequestParam Integer count){

        return  fetchData.findAll().stream()
                .limit(count).collect(Collectors.toList());

    }

    @GetMapping(path="/tvshows",params = "movieType", produces = "application/json")
    List<ContentModel> getDataListed(@RequestParam String movieType){
        return  fetchData.findAll().stream()
                .filter(s->s.getListed_in().contains(movieType))
                .collect(Collectors.toList());

    }

    @GetMapping(path="/tvshows",params = "country", produces = "application/json")
    List<ContentModel> getDataBasedOnCountry(@RequestParam String country){
        return  fetchData.findAll().stream()
                .filter(s->s.getCountry().contains(country))
                .collect(Collectors.toList());

    }

    @RequestMapping(path="/tvshows",params ={ "startDate", "endDate"}, produces = "application/json")
    List<ContentModel> getDataBasedOnDateRange(@RequestParam String startDate, @RequestParam String endDate ) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate start= dateFormat.parse(startDate).toInstant().
                atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = dateFormat.parse(endDate).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        return  fetchData.findAll().stream()
                .filter(movieRow ->
                {
                    try {
                        if (movieRow.getDate_added().trim().equals("")) {
                            return false;
                        }
                        LocalDate dateAdded = dateFormat.parse(movieRow.getDate_added().replaceAll("\"", "").trim())
                                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        return dateAdded.isAfter(start) && dateAdded.isBefore(end);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toList());

    }
}
