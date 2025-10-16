package com.example.springbootdb;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/banking-users")
public class BankingUserController
{
    @GetMapping
    public List<BankingUser> getEngineers() {
        return List.of(new BankingUser("446916010129332", "Jonathan", 27),
                new BankingUser("298318321932132", "Joji", 67),
                new BankingUser("8231939123","Leclerc", 16 ));
    }
}