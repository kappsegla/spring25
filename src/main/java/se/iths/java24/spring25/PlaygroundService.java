package se.iths.java24.spring25;

import se.iths.java24.spring25.domain.entity.Playground;

import java.util.List;

public interface PlaygroundService {
    List<Playground> getAllPlaygrounds();
    void addRandomPlaygrounds(int count);
    Playground addPlayground(Playground playground);
}
