package com.create.runnerz.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonLoader implements CommandLineRunner {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RunJsonLoader.class);
    private final JdbcClientRunRepository jdbcClientRunRepository;
    private final RunRepository runRepository;
    private final ObjectMapper mapper;

    public RunJsonLoader(JdbcClientRunRepository jdbcClientRunRepository, RunRepository runRepository, ObjectMapper mapper) {
        this.jdbcClientRunRepository = jdbcClientRunRepository;
        this.runRepository = runRepository;
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) throws Exception {
        //if (jdbcClientRunRepository.count() == 0) {
        if (runRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                Runs jsonRuns = mapper.readValue(inputStream, Runs.class);
                log.info("Reading {} jsonRuns from json file data and saving into database", jsonRuns.runs().size());
                jdbcClientRunRepository.saveAll(jsonRuns.runs());
            } catch (IOException e) {
                throw new RuntimeException("Error reading jsonRuns from json file", e);
            }
        } else {
            log.info("Not reading runs from json file");
        }
    }
}
