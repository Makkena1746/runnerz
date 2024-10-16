package com.create.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository runRepository;

    public RunController(RunRepository repository) {
        this.runRepository = repository;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to Runnerz Application";
    }

    @GetMapping("")
    public List<Run> runs() {
        return runRepository.getRuns();
    }

    @GetMapping("/{id}")
    public Run findById(@PathVariable Integer id) {
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
        runRepository.addRun(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void updateRun(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.updateRun(run, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteRun(@PathVariable Integer id) {
        runRepository.deleteRun(id);
    }
}
