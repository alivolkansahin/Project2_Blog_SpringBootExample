package com.volkans.avsblog.entity;

import com.volkans.avsblog.entity.enums.ETextStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    @ColumnTransformer(write = "lower(?)")
    private String content;

    @Builder.Default
    private LocalDateTime commentDate = LocalDateTime.now().withNano(0);

    @ManyToOne
    private Post post;

    @ManyToMany
    @Builder.Default
    private List<User> liker = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ETextStatus status = ETextStatus.SHOWN;
}
