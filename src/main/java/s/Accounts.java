package s;

import jakarta.persistence.*;

@Entity
public class Accounts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "email", nullable = true, length = 20)
    private String email;
    @Basic
    @Column(name = "password", nullable = true, length = 30)
    private String password;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @Basic
    @Column(name = "role_id", nullable = false, length = 36)
    private String roleId;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Roles rolesByRoleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Accounts accounts = (Accounts) o;

        if (id != null ? !id.equals(accounts.id) : accounts.id != null) return false;
        if (email != null ? !email.equals(accounts.email) : accounts.email != null) return false;
        if (password != null ? !password.equals(accounts.password) : accounts.password != null) return false;
        if (isDeleted != null ? !isDeleted.equals(accounts.isDeleted) : accounts.isDeleted != null) return false;
        if (roleId != null ? !roleId.equals(accounts.roleId) : accounts.roleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }

    public Roles getRolesByRoleId() {
        return rolesByRoleId;
    }

    public void setRolesByRoleId(Roles rolesByRoleId) {
        this.rolesByRoleId = rolesByRoleId;
    }
}
