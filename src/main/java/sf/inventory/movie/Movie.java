package sf.inventory.movie;

import javax.persistence.*;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(unique = true)
    private String title;

    private int releaseYear;

    private boolean uhd;

    private boolean bluray;

    private boolean dvd;

    public Movie() {
    }

    public Movie(String title, int year, boolean uhd, boolean bluray, boolean dvd) {
        this.title = title;
        this.releaseYear = year;
        this.uhd = uhd;
        this.bluray = bluray;
        this.dvd = dvd;
    }

    private Movie(MovieBuilder builder) {
        this.title = builder.title;
        this.releaseYear = builder.year;
        this.uhd = builder.uhd;
        this.bluray = builder.bluray;
        this.dvd = builder.dvd;
    }

    public static class MovieBuilder {
        private String title;
        private int year;
        private boolean uhd;
        private boolean bluray;
        private boolean dvd;

        public MovieBuilder(String title, int year) {
            this.title = title;
            this.year = year;
        }

        public MovieBuilder setUHD(boolean flag) {
            this.uhd = flag;
            return this;
        }

        public MovieBuilder setBluray(boolean flag) {
            this.bluray = flag;
            return this;
        }

        public MovieBuilder setDvd(boolean flag) {
            this.dvd = flag;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public boolean getUhd() {
        return uhd;
    }

    public void setUhd(boolean uhd) {
        this.uhd = uhd;
    }

    public boolean getBluray() {
        return bluray;
    }

    public void setBluray(boolean bluray) {
        this.bluray = bluray;
    }

    public boolean getDvd() {
        return dvd;
    }

    public void setDvd(boolean dvd) {
        this.dvd = dvd;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + releaseYear +
                ", uhd=" + uhd +
                ", bluray=" + bluray +
                ", dvd=" + dvd +
                '}';
    }
}
