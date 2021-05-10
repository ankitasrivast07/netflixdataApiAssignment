package com.movie.netflixdata.models;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Table(name = "netflix_titles")
public class ContentModel

{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "show_id")
    private String show_id;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "cast",columnDefinition = "Text")
    private String cast;

    @Column(name = "country")
    private String country;

    @Column(name = "date_added")
    private String date_added;




    @Column(name = "release_year")
    private String release_year;

    @Column(name = "rating")
    private String rating;

    @Column(name = "duration")
    private String duration;

    @Column(name = "listed_in")
    private String listed_in;


    @Column(name = "description",columnDefinition = "Text")
    private String description;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getShow_id() {
        return show_id;
    }

    public void setShow_id(String show_id) {
        this.show_id = show_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getListed_in() {
        return listed_in;
    }

    public void setListed_in(String listed_in) {
        this.listed_in = listed_in;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ContentModel{" +
                "show_id='" + show_id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", cast='" + cast + '\'' +
                ", country='" + country + '\'' +
                ", date_added='" + date_added + '\'' +
                ", release_year='" + release_year + '\'' +
                ", rating='" + rating + '\'' +
                ", listed_in='" + listed_in + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
