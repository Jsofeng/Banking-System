import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankUserInfoService {

    private final BankUserInfoRepository BankUserInfoRepository;
    private final AiService aiService;

    public BankUserInfoService(
            BankUserInfoRepository BankUserInfoRepository,
            AiService aiService
    ) {
        this.BankUserInfoRepository = BankUserInfoRepository;
        this.aiService = aiService;
    }

    public List<BankUserInfo> getAllBankUserInfos() {
        return BankUserInfoRepository.findAll();
    }

    public void insertBankUserInfo(
            BankUserInfo BankUserInfo) {
        String prompt = " Based on the programming tech stack %s that %s has givenProvide a full learning path and recommendations for this person.".formatted(BankUserInfo.getTechStack(),BankUserInfo.getName()    );
        String chatRes = aiService.chat(prompt);
        BankUserInfo.setLearningPathRecommendation(chatRes);
        BankUserInfoRepository.save(BankUserInfo);
    }

    public BankUserInfo getBankUserInfoById(
            Integer id) {
        return BankUserInfoRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + " not found"));
    }

    public void deleteBankUserInfo(Integer id) {
        boolean exists = BankUserInfoRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(id + " not found");
        }
        BankUserInfoRepository.deleteById(id);
    }

    public void updateBankUserInfo(Integer id, BankUserInfo update) {
        BankUserInfo BankUserInfo = BankUserInfoRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + " not found"));
        BankUserInfo.setName(update.getName());
        BankUserInfo.setTechStack(update.getTechStack());
        BankUserInfoRepository.save(BankUserInfo);
    }
}