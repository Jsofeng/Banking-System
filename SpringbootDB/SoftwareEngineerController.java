package com.example.demo;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController
{
    @GetMapping
    public List<SoftwareEngineer> getEngineers() {
        return List.of(new SoftwareEngineer("java, python, node.js, react", "Jonathan", 1),
                new SoftwareEngineer("spring-boot, kotlin, python", "Jamailia", 2));
    }
}
