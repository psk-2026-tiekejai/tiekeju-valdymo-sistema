package lt.vu.mif.psk.tiekejai.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "registration_code", nullable = false, unique = true, length = 50)
    private String registrationCode;

    @Column(length = 255)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    @Column(name = "opt_lock_version", nullable = false)
    private Long version;

    public Supplier() {
    }

    public Supplier(String name, String registrationCode, String email, String phone) {
        this.name = name;
        this.registrationCode = registrationCode;
        this.email = email;
        this.phone = phone;
    }

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Supplier supplier)) {
            return false;
        }
        return registrationCode != null && Objects.equals(registrationCode, supplier.registrationCode);
    }

    @Override
    public int hashCode() {
        return registrationCode != null ? registrationCode.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Supplier{id=%s, name='%s'}".formatted(id, name);
    }
}
