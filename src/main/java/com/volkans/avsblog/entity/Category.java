package com.volkans.avsblog.entity;


import com.volkans.avsblog.entity.enums.ECategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @ColumnTransformer(write = "lower(?)")
    @Size(max = 50, message = "Name must not exceed {max} characters long")
    private String name;

    @Column(nullable = false)
    @ColumnTransformer(write = "lower(?)")
    private String description;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ECategoryStatus status = ECategoryStatus.AVAILABLE;

}
