package com.movie.netflixdata.controller;

import com.movie.netflixdata.models.ContentModel;
import com.movie.netflixdata.repository.FetchData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@RestController
public class FetchDataFromDb {
    private static final Logger LOGGER = LogManager.getLogger(FetchDataFromDb.class);
    private static final String  FILE_PATH = "src/main/resources/data/netflix_titles.csv";
    @Autowired
    FetchData fetchData;

    @Autowired
    private FetchData netflixData;

    @PostMapping(path = "tvshows", consumes="application/json", params = "flag") //  flag= db or csv(define where data need to save)
    String saveNetflix(@RequestBody ContentModel model, @RequestParam String flag) throws IOException {
         if(flag=="db") {
             //saving in db
          netflixData.save(model);

         }else if(flag=="csv"){
        // for saving in csv

                 FileWriter csvWriter = new FileWriter(FILE_PATH,true);
                 csvWriter.append("\n");
                 csvWriter.append(model.getShow_id());
                 csvWriter.append(",");
                 csvWriter.append(model.getType());
                 csvWriter.append(",");
                 csvWriter.append(model.getTitle());
                 csvWriter.append(",");
                 csvWriter.append(model.getDirector());
                 csvWriter.append(",");
                 csvWriter.append(model.getCast());
                 csvWriter.append(",");
                 csvWriter.append(model.getCountry());
                 csvWriter.append(",");
                 csvWriter.append(model.getDate_added());
                 csvWriter.append(",");
                 csvWriter.append(model.getRelease_year());
                 csvWriter.append(",");
                 csvWriter.append(model.getRating());
                 csvWriter.append(",");
                 csvWriter.append(model.getDuration());
                 csvWriter.append(",");
                 csvWriter.append(model.getListed_in());
                 csvWriter.append(",");
                 csvWriter.append(model.getDescription());
                 csvWriter.flush();
                 csvWriter.close();
             }

        return "";
    }

//Fetching data from database


    @GetMapping(path="/tvshows", params = "count", produces = "application/json")

   public List<ContentModel> getNumData( HttpServletRequest request,
                                   HttpServletResponse response, @RequestParam Integer count){
        Instant startTime,endTime;
        startTime = Instant.now();
        List<ContentModel> netflixDataService=new ArrayList<>();
        try{
            netflixDataService=  fetchData.findAll().stream()
                           .limit(count).collect(Collectors.toList());
        }catch(Exception e){
            LOGGER.error("Error is "+e.getMessage());
        }
        endTime = Instant.now();
        long time = Duration.between(startTime,endTime).toMillis();
        LOGGER.info("Process of execution of the request :"+time+" milliseconds .");
        response.setHeader("X-TIME-TO-EXECUTE", String.valueOf(time));
       return netflixDataService;
    }

    @GetMapping(path="/tvshows",params = "movieType", produces = "application/json")
    List<ContentModel> getDataListed(HttpServletRequest request,
                                     HttpServletResponse response, @RequestParam String movieType){
        Instant startTime,endTime;
        startTime = Instant.now();
        List<ContentModel> netflixDataService=new ArrayList<>();
        try{
            netflixDataService=  fetchData.findAll().stream()
                    .filter(s->s.getListed_in().contains(movieType))
                    .collect(Collectors.toList());
        }catch(Exception e){
            LOGGER.error("Error is "+e.getMessage());
        }
        endTime = Instant.now();
        long time = Duration.between(startTime,endTime).toMillis();
        LOGGER.info("Process of execution of the request :"+time+" milliseconds .");
        response.setHeader("X-TIME-TO-EXECUTE", String.valueOf(time));
        return netflixDataService;

    }

    @GetMapping(path="/tvshows",params = "country", produces = "application/json")
    List<ContentModel> getDataBasedOnCountry(HttpServletRequest request,
                                             HttpServletResponse response,@RequestParam String country){
        Instant startTime,endTime;
        startTime = Instant.now();
        List<ContentModel> netflixDataService=new ArrayList<>();
        try{
            netflixDataService=  fetchData.findAll().stream()
                    .filter(s->s.getCountry().contains(country))
                    .collect(Collectors.toList());
        }catch(Exception e){
            LOGGER.error("Error is "+e.getMessage());
        }
        endTime = Instant.now();
        long time = Duration.between(startTime,endTime).toMillis();
        LOGGER.info("Process of execution of the request :"+time+" milliseconds .");
        response.setHeader("X-TIME-TO-EXECUTE", String.valueOf(time));
        return netflixDataService;


    }

    @RequestMapping(path="/tvshows",params ={ "startDate", "endDate"}, produces = "application/json")
    List<ContentModel> getDataBasedOnDateRange(HttpServletRequest request,
                                               HttpServletResponse response,@RequestParam String startDate, @RequestParam String endDate ) throws ParseException {
        Instant startTime,endTime;
        startTime = Instant.now();
        List<ContentModel> netflixDataService=new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate start= dateFormat.parse(startDate).toInstant().
                atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = dateFormat.parse(endDate).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        try{
            netflixDataService=  fetchData.findAll().stream()
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
        }catch(Exception e){
            LOGGER.error("Error is "+e.getMessage());
        }
        endTime = Instant.now();
        long time = Duration.between(startTime,endTime).toMillis();
        LOGGER.info("Process of execution of the request :"+time+" milliseconds .");
        response.setHeader("X-TIME-TO-EXECUTE", String.valueOf(time));
        return netflixDataService;


    }
}
