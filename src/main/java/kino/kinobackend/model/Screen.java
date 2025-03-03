package kino.kinobackend.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int screenId;
    private int screen_number;
    private int max_rows;
    private int seats_per_row;

    @OneToMany(mappedBy = "screen")
    private Set<Showing> shows = new HashSet<>();


    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public int getScreen_number() {
        return screen_number;
    }

    public void setScreen_number(int screen_number) {
        this.screen_number = screen_number;
    }

    public int getMax_rows() {
        return max_rows;
    }

    public void setMax_rows(int max_rows) {
        this.max_rows = max_rows;
    }

    public int getSeats_per_row() {
        return seats_per_row;
    }

    public void setSeats_per_row(int seats_per_row) {
        this.seats_per_row = seats_per_row;
    }
}
