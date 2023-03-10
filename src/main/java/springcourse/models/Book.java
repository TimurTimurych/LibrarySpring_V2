package springcourse.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30,
            message = "Name should be between 2 and 30 charters")
    private String title;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30,
            message = "Name should be between 2 and 30 charters")
    private String author;

    @Min(value = 1700, message = "Age should be greater that 0")
    private int year;

    @Column(name = "taken_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenDate;

    @Transient
    private boolean delay;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "")
    private Person person;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }

    public boolean isDelay() {
        return delay;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }
}

