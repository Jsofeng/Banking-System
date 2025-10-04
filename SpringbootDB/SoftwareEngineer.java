package com.example.demo;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Objects;

@Entity
public class SoftwareEngineer {

    @id
    private Integer id;
    private String name;
    private String techStack;

    public SoftwareEngineer(String techStack, String name, Integer id)
    {
        this.techStack = techStack;
        this.name = name;
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getTechStack()
    {
        return techStack;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setTechStack(String techStack)
    {
        this.techStack = techStack;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        SoftwareEngineer that = (SoftwareEngineer) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
               Objects.equals(techStack, that.techStack);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, techStack);
    }
}
