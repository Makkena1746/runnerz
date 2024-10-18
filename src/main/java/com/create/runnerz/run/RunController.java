package com.create.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final JdbcClientRunRepository jdbcClientRunRepository;

    private final RunRepository runRepository;

    public RunController(JdbcClientRunRepository repository, RunRepository runRepository) {
        this.jdbcClientRunRepository = repository;
        this.runRepository = runRepository;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to Runnerz Application";
    }

    @GetMapping("")
    public List<Run> runs() {
//        return jdbcClientRunRepository.getRuns();
        return runRepository.findAll();
    }

    @GetMapping("/{id}")
    public Run findById(@PathVariable Integer id) {
//        Optional<Run> run = jdbcClientRunRepository.findById(id);
        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new RunNotFoundException();
        } else {
            return run.get();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void addRun(@Valid @RequestBody Run run) {
//        jdbcClientRunRepository.addRun(run);
        runRepository.save(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void updateRun(@Valid @RequestBody Run run, @PathVariable Integer id) {
//        jdbcClientRunRepository.updateRun(run, id);
        runRepository.save(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteRun(@PathVariable Integer id) {
//        jdbcClientRunRepository.deleteRun(id);
        runRepository.deleteById(id);
    }

    @GetMapping("/location/{location}")
    List<Run> findAllByLocation(@PathVariable Location location) {
        return runRepository.findAllByLocation(location);
    }

    @GetMapping("/location/{location}/miles/{miles}")
    List<Run> findAllByLocationAndMiles(@PathVariable Location location, @PathVariable Integer miles) {
        return runRepository.findAllByLocationAndMiles(location, miles);
    }


}
