package com.volkans.avsblog.entity;

import com.volkans.avsblog.annotation.PasswordDigitValidation;
import com.volkans.avsblog.annotation.UsernameSpecialCharacterValidation;
import com.volkans.avsblog.entity.enums.EUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnTransformer(write = "lower(?)")
    private String firstName;

    @Column(nullable = false)
    @ColumnTransformer(write = "lower(?)")
    private String lastName;

    @Column(unique = true, nullable = false)
    @ColumnTransformer(write = "lower(?)")
    @Email(message = "Check your email address for inappropriate characters!")
    private String email;

    @Column(nullable = false, length = 40)
    @PasswordDigitValidation
    @Size(min = 6, max = 40, message = "Password must be between {min} and {max} characters long")
    private String password;

    @Column(unique = true, nullable = false, length = 20)
    @ColumnTransformer(write = "lower(?)")
    @UsernameSpecialCharacterValidation
    @Size(min = 4, max = 20, message = "Username must be between {min} and {max} characters long")
    private String username;

    @Builder.Default
    private String photo = "https://cdn-icons-png.flaticon.com/512/3364/3364044.png";

    @Builder.Default
    private LocalDateTime registerDate = LocalDateTime.now().withNano(0);
//    private LocalDateTime registerDate = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EUserStatus status = EUserStatus.ACTIVE;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    private List<Post> favPosts = new ArrayList<>();

    @Builder.Default
    @ManyToMany//    @Fetch(FetchMode.SELECT)
    private List<Category> favCategories = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "users_follow_relations",
            joinColumns = @JoinColumn(name="following_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="follower_id", referencedColumnName = "id"))
    private List<User> followers = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "users_follow_relations",
            joinColumns = @JoinColumn(name="follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="following_id", referencedColumnName = "id"))
    private List<User> followings = new ArrayList<>();

}
