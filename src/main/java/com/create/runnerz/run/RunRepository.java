package com.create.runnerz.run;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RunRepository extends ListCrudRepository<Run, Integer> {

     List<Run> findAllByLocation(Location location);

     List<Run> findAllByLocationAndMiles(Location location, Integer miles);

}
