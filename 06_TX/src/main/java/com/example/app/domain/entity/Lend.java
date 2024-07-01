package com.example.app.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Lend")
public class Lend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //User 1-N Lend
    @ManyToOne
    @JoinColumn(name = "username",foreignKey = @ForeignKey(name="FK_LEND_USER",
    foreignKeyDefinition = "FOREIGN KEY(username) REFERENCES User(username) ON DELETE CASCADE ON UPDATE CASCADE" ))
    private User user;

    //Book 1-N Lend
    @ManyToOne
    @JoinColumn(name = "bookcode",foreignKey = @ForeignKey(name="FK_LEND_BOOK",
    foreignKeyDefinition = "FOREIGN KEY(bookcode) REFERENCES Book(bookcode) ON DELETE CASCADE ON UPDATE CASCADE" ))
    private Book book;

    @Column(name="regdate")
    private LocalDate regDate;

    @Column(name="returndate")
    private LocalDate returnDate;


}
