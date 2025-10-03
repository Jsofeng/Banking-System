import org.springframework.data.jpa.repository.JpaRepository;

public interface BankUserRepo
        extends JpaRepository<BankUserInfo, Integer> {
}