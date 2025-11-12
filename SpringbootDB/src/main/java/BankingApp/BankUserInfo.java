import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class BankUserInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String techStack;
    @Column(columnDefinition = "TEXT")
    private String learningPathRecommendation;

    public BankUserInfo() {
    }

    public BankUserInfo(Integer id, String name, Integer debitCardNumber, Integer balance, String techStack, String learningPathRecommendation) {
        this.id = id;
        this.name = name;
	this.debitCardNumber = debitCardNumber;
	this.balance = balance;
        this.techStack = techStack;
        this.learningPathRecommendation = learningPathRecommendation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDebitCardNumber() {

	return debitCardNumber;
    }

    public void setDebitCardNumber(String debitCardNumber) {
	this.debitCardNumber = debitCardNumber;
    }
    public void setName(String name) {
        this.name = name;
    }


    public double getBalance() {
	return balance;

    }
    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public String getLearningPathRecommendation() {
        return learningPathRecommendation;
    }

    public void setLearningPathRecommendation(String learningPathRecommendation) {
        this.learningPathRecommendation = learningPathRecommendation;
    }


    public String accountDetails() {

    return "Account Number: " + this.debitCardNumber
                + "\nAccount Holder: " + this.name
                + "\nBalance: $" + String.format("%.2f", (double) this.balance);

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BankUserInfothat = (BankUserInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(techStack, that.techStack) && Objects.equals(learningPathRecommendation, that.learningPathRecommendation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, techStack, learningPathRecommendation);
    }
}
