package com.rehair.rehair.repository;

import com.rehair.rehair.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
