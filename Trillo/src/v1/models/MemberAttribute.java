package v1.models;

public class MemberAttribute extends Attribute{
    private User user;
    public MemberAttribute(String fieldName, User user) {
        super(fieldName);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
