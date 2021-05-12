package com.movie.netflixdata.repository;

import com.movie.netflixdata.models.ContentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FetchData extends JpaRepository<ContentModel, Integer> {


    @Override
    List<ContentModel> findAll();

}
