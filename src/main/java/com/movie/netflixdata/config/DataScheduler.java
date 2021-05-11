package com.movie.netflixdata.config;

import com.movie.netflixdata.models.ContentModel;
import com.movie.netflixdata.repository.FetchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

@EnableScheduling
@Configuration
public class DataScheduler{
    @Autowired
    private FetchData netflixData;

    @Scheduled(fixedDelay = 5000)
    public void saveNetflixData(){

        String pathOfFile= "data/netflix_titles.csv";
        String line="";
        try{
            BufferedReader csvReader = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().
                            getResourceAsStream(pathOfFile)));
            int row=0;
            while((line=csvReader.readLine())!=null){
                String [] content=line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                ContentModel nd=new ContentModel();
//                System.out.println(content[0]+"my");
                if(row==0){
                    row++;
                }
                else {
                    nd.setShow_id(content[0]);
                    nd.setType(content[1]);
                    nd.setTitle(content[2]);
                    nd.setDirector(content[3]);
                    nd.setCast(content[4]);
                    nd.setCountry(content[5]);
                    nd.setDate_added(content[6]);
                    nd.setRelease_year(content[7]);
                    nd.setRating(content[8]);
                    nd.setDuration(content[9]);
                    nd.setListed_in(content[10]);
                    nd.setDescription(content[11]);
                    netflixData.save(nd);
                }

//                System.out.println(nd.toString());

            }
        }catch(IOException e){
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }


    }

}
