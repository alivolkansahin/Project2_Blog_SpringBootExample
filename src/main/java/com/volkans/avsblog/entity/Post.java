package com.volkans.avsblog.entity;

import com.volkans.avsblog.entity.enums.ETextStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    @ColumnTransformer(write = "lower(?)")
    @Size(max = 150, message = "Title must not exceed {max} characters long")
    private String title;

    @Column(nullable = false)
    @ColumnTransformer(write = "lower(?)")
    private String content;

    @ManyToOne
    private User user;

    @Column(name = "published_at")
    @Builder.Default
    private LocalDateTime publishDate = LocalDateTime.now().withNano(0);

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    private List<User> liker = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ETextStatus status = ETextStatus.SHOWN;
}
