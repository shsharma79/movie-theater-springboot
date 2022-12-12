package com.jpmc.theater.repository;

import com.jpmc.theater.model.Theater;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SimpleTheaterRepositoryTest {

  @Autowired private SimpleTheaterRepository repository;

  @Test
  public void shouldReturnNonEmptyTheaterList() {
    assertThat(repository.getTheaters()).isNotEmpty();
  }

  @Test
  public void whenValidTheaterId_ShouldReturnTheater() {
    Optional<Theater> o = repository.getTheaterById(1L);
    assertThat(o).isPresent();
    Theater t = o.get();
    assertThat(t.getTheaterName()).isEqualTo("Demo Theater");
  }

  @Test
  public void whenInvalidTheaterId_ShouldReturnEmptyOptional() {
    Optional<Theater> o = repository.getTheaterById(-1L);
    assertThat(o).isNotPresent();
  }
}
