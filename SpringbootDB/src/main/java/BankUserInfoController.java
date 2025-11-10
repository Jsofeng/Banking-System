package com.example.springbootdb;
import java.util.List;
import java.util.ArrayList;
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

    @GetMapping(value = "/display", produces = "text/plain")
	public String displayUserInfo() {

	ArrayList<BankingUser> accounts = new ArrayList<>();


	accounts.add(new BankingUser("123456789", "Lewis Hamilton", 44, 9012932));
        accounts.add(new BankingUser("1601923921", "Charles Leclerc", 16, 16161616));

	StringBuilder sb = new StringBuilder();
	for(BankingUser u : accounts) {
		sb.append(u.accountDetails()).append("\n\n");


      }
	return sb.toString();

    }

   @GetMapping(value = "/save", produces = "text/plain")
    public String fileReading() {
        ArrayList<BankingUser> accounts = new ArrayList<>();
        accounts.add(new BankingUser("5152006", "Kang Haerin", 15, 92013201));
        accounts.add(new BankingUser("08262005", "Pharita", 26, 2925232));

        FileManager.saveAccounts("bb.txt", accounts);
        StringBuilder sb = new StringBuilder();
        sb.append("ACCOUNTS SAVED: \n\n");
        for(BankingUser u : accounts) {
            sb.append(u.accountDetails()).append("\n\n");
        }
        return sb.toString();
    }




}

