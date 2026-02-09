package models;

import java.util.ArrayList;
import java.util.List;

public class Theater {
    private String id;
    private String name;
    private List<Showtime> showtimes;

    public Theater(String id, String name, List<Showtime> showtimes) {
        this.id = id;
        this.name = name;
        this.showtimes = showtimes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public List<Showtime> getShowtimesForMovie(Movie movie) {
        List<Showtime> result = new ArrayList<>();
        for(Showtime showtime: this.showtimes){
            if(showtime.getMovie().movieId().equals(movie.movieId())) {
                result.add(showtime);
            }
        }

        return result;
    }
}
