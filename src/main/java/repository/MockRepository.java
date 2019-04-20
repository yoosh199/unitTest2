package repository;

import domain.Champion;

import java.util.List;

public interface MockRepository {
    List<Champion> findAll();

    Champion findByname(String name);
}
