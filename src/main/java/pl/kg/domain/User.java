package pl.kg.domain;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="START_DATE")
    private LocalDate startDate;

    @ManyToOne(targetEntity = Licence.class)
    @JoinColumn(name = "LICENCE_ID")
    private Licence licence;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    User() {
    }

    User(String firstName, String lastName, LocalDate startDate, Licence licence) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.licence = licence;
        this.status = UserStatus.ACTIVE;
    }

    Long getId() {
        return id;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    LocalDate getStartDate() {
        return startDate;
    }

    Licence getLicence() {
        return licence;
    }

    void setLicence(Licence licence) {
        this.licence = licence;
    }

    UserStatus getStatus() {
        return status;
    }

    void deactivateUser() {
        if (UserStatus.ACTIVE.equals(this.status)) {
            this.status = UserStatus.INATIVE;
        }
    }

    void activateUser() {
        if (UserStatus.INATIVE.equals(this.status)) {
            this.status = UserStatus.ACTIVE;
        }
    }
}
