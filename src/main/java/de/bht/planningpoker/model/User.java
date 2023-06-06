package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username may not be blank")
    @Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z ]{1,32}$", message = "Username must be lower or upper case letter from a to z and can have white spaces")
    @Column(nullable = false, length = 32)
    private String username;

    @NotNull(message = "Active may not be null")
    @Column(nullable = false)
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private @Valid Role role;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)
    private @Valid Team team;

    @PastOrPresent(message = "CreatedAt must be in the past or in the present")
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @PastOrPresent(message = "UpdatedAt must be in the past or in the present")
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public boolean isActive() {
        return active;
    }

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
