package service;

import domain.Champion;
import repository.MockRepository;

import java.util.List;

public class MockService {
    private final MockRepository mockRepository;

    public MockService(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    public List<Champion> findAllChampions() {
        return mockRepository.findAll();
    }

    public Champion findByname(String name) {
        final Champion champion = mockRepository.findByname(name);
        return champion;
    }
}
