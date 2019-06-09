package pl.ms.library.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import pl.ms.library.book.BookEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    @NotBlank
    private String username;

    private BigDecimal chargeAmount;

    @OneToMany(mappedBy = "currentUser", cascade = CascadeType.ALL)
    private List<BookEntity> borrowedBooks = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public List<BookEntity> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BookEntity> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void addToBorrowedBook(BookEntity book) {
        borrowedBooks.add(book);
    }

    public void removeFromBorrowedBook(BookEntity book) {
        borrowedBooks.remove(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return getId().equals(that.getId()) &&
                getUsername().equals(that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername());
    }
}
