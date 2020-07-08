package pl.kg.domain;

import static java.util.Objects.isNull;

import java.time.LocalDate;

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
class Licence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LICENCE_TYPE")
    @Enumerated(EnumType.STRING)
    private LicenceType licenceType;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

    @Column(name = "TOKENS")
    private long tokens;

    @Column(name = "START_DATE")
    private LocalDate startDate;


    @Column(name = "EXPIRY_DATE")
    private LocalDate expiryDate;

    public Licence() {

    }

    public Licence(User user, LicenceType licenceType, LocalDate startDate) {
        this.user = user;
        this.licenceType = licenceType;
        this.startDate = startDate;
        setValuesByLicenceType();
    }

    public void changeLicence(LicenceType licenceType, LocalDate startDate) {
        if(!isImmutablePeriodOver(startDate)) {
            return;
        }
        this.licenceType = licenceType;
        this.startDate = startDate;
        long ownedTokens = this.tokens;
        setValuesByLicenceType();
        this.tokens += ownedTokens;
    }

    private boolean isImmutablePeriodOver(LocalDate newStartDate) {
        return this.startDate.plusMonths(1).compareTo(newStartDate) < 0;
    }

    private void setValuesByLicenceType() {
        if (isNull(this.licenceType)) {
            throw new IllegalArgumentException("LicenceType can not be null!");
        }
        switch (this.licenceType) {
        case FREE:
            this.tokens = 0;
            this.expiryDate = this.startDate.plusMonths(1);
            break;
        case PREMIUM:
            this.tokens = 100;
            this.expiryDate = this.startDate.plusYears(1);
            break;
        case PRO:
            this.tokens = 10000;
            this.expiryDate = null;
            break;
        default:
            throw new IllegalArgumentException("Invalid LicenceType value: " + this.licenceType);
        }
    }

    public Long getId() {
        return id;
    }

    public LicenceType getLicenceType() {
        return licenceType;
    }

    public long getTokens() {
        return tokens;
    }

    public long getUserId() {
        return user.getId();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
