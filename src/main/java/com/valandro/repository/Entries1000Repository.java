package com.valandro.repository;

import com.valandro.data.Entries1000;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Entries1000Repository extends MongoRepository<Entries1000, Long> {
}