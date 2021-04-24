package entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "season")
@Entity
public class Season {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    @Column
    private Integer number;

    @JsonProperty
    @Column(name = "start_date")
    private Date startDate;

    @JsonProperty
    @Column(name = "finish_date")
    private Date finishDate;

    @JsonProperty
    @Column(name = "series_count")
    private Integer seriesCount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "serial_id")
    private Serial serial;

    @JsonProperty("episodes")
    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seria> seriaSet = new HashSet<>();

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(Integer seriesCount) {
        this.seriesCount = seriesCount;
    }

    public Set<Seria> getSeriaSet() {
        return seriaSet;
    }

    public void setSeriaSet(Set<Seria> seriaList) {
        this.seriaSet = seriaList;
    }

    public Season() {
    }

    public Long getId() {
        return id;
    }

    public Serial getSerial() {
        return serial;
    }

    public void setSerial(Serial serial) {
        this.serial = serial;
    }
}
