package Airline_Ticket_Management_System.model;

public class User {
    private final int id;
    private String name;
    private String emailId;
    private String contactNumber;

    public User(int id, String name, String emailId, String contactNumber) {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
    }

    public int getId() {
        return id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
