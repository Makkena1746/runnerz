package com.create.runnerz.run;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping
    public List<Run> runs() {
        return runRepository.getRuns();
    }

    @GetMapping("/{id}")
    public Run findById(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return run.get();
        }
    }
}
