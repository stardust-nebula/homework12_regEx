import java.util.List;

public class Document {

    public List<String> docNumberList;
    public String phoneNumber;
    public String emailAddress;

    public Document(List<String> docNumberList, String phoneNumber, String emailAddress) {
        this.docNumberList = docNumberList;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public List<String> getDocNumberList() {
        return docNumberList;
    }

    public void setDocNumberList(List<String> docNumberList) {
        this.docNumberList = docNumberList;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
