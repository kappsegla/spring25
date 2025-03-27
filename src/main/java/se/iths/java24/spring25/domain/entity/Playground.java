package se.iths.java24.spring25.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Playground {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    public Playground() {
    }

    public Playground(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Playground{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
