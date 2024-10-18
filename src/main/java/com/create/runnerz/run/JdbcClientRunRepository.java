package com.create.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcClientRunRepository.class);

    // in-memory crud operations
    /*List<Run> runs = new ArrayList<>();
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

    void addRun(Run run) {
        runs.add(run);
    }

    void updateRun(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        existingRun.ifPresent(value -> runs.set(runs.indexOf(value), run));
    }

    void deleteRun(Integer id) {
        runs.removeIf(run -> run.id().equals(id));
    }*/

    // H2 crud operations
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Run> getRuns() {
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    Optional<Run> findById(int id) {
        return jdbcClient.sql("select * from run r where r.id = ?")
                .param(id)
                .query(Run.class)
                .optional();
    }

    public void addRun(Run run) {
        var updated = jdbcClient.sql("insert into run values (?, ?, ?, ?, ?, ?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    void updateRun(Run run, Integer id) {
        var updated = jdbcClient.sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();
        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    void deleteRun(int id) {
        var deleted = jdbcClient.sql("delete from run where id = ?")
                .param(id)
                .update();
        Assert.state(deleted == 1, "Failed to delete run " + id);
    }

    void saveAll(List<Run> runs) {
        runs.forEach(this::addRun);
    }

    Integer count() {
        return jdbcClient.sql("select * from run").query().listOfRows().size();
    }

}
