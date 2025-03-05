package kino.kinobackend.screen;

import jakarta.persistence.*;

@Entity
public class ScreenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="screen_id")
    private int screenId;
    private int screen_number;
    private int maxRows;
    private int seatsPerRow;

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

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }
}
