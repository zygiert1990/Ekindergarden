package ekindergarten.security.jwt;

public class JwtUser {
    private String subject;
    private Long id;
    private String role;

    public void setUserName(String subject) {
        this.subject = subject;
    }

    public void setId(long userId) {
        this.id = userId;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public String getSubject() {
        return subject;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
