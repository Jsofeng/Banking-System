import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bank_user")
public class BankUserInfoController {

    private final BankUserInfoService BankUserInfoService;

    public BankUserInfoController(BankUserInfoService BankUserInfoService) {
        this.BankUserInfoService = BankUserInfoService;
    }

    @GetMapping
    public List<BankUserInfo> getEngineers() {
        return BankUserInfoService.getAllBankUserInfos();
    }

    @GetMapping("{id}")
    public BankUserInfo getEngineerById(
            @PathVariable Integer id
    ) {
        return BankUserInfoService.getBankUserInfoById(id);
    }


    @PostMapping
    public void addNewBankUserInfo(
            @RequestBody BankUserInfo BankUserInfo
    ) {
        BankUserInfoService.insertBankUserInfo(BankUserInfo);
    }

    @DeleteMapping("{id}")
    public void deleteBankUserInfo(@PathVariable Integer id) {
        BankUserInfoService.deleteBankUserInfo(id);
    }

    @PutMapping("{id}")
    public void updateBankUserInfo(@PathVariable Integer id,
                                       @RequestBody BankUserInfo BankUserInfo) {
        BankUserInfoService.updateBankUserInfo(id, BankUserInfo);
    }
}