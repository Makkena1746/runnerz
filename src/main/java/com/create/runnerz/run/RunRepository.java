package com.create.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    List<Run> runs = new ArrayList<>();

    @PostConstruct
    public void init() {
        runs.add(new Run(1, "First Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 3, Location.INDOOR));
        runs.add(new Run(2, "Second Run", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 5, Location.OUTDOOR));
    }

    public List<Run> getRuns() {
        return runs;
    }

    public Optional<Run> findById(int id) {
        return runs.stream().filter(run -> run.id() == id).findFirst();
    }
}
