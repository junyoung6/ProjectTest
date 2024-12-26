package com.project.rentcar.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Rent")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentId")
    private Long rentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_RENT_USER",
    foreignKeyDefinition = "FOREIGN KEY(userId) REFERENCES user(userId) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId", foreignKey = @ForeignKey(name = "FK_RENT_USER",
    foreignKeyDefinition = "FOREIGN KEY(carId) REFERENCES user(carId) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Car car;

    @Column(name="rentDate")
    private LocalDate rentDate;

    @Column(name="returnDate")
    private LocalDate returnDate;
}
