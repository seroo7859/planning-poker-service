package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BacklogItem implements Cloneable {

    @Transient
    public static final String NUMBER_PREFIX = "US";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 5, message = "Number must be 5 characters long")
    @Pattern(regexp = "^" + NUMBER_PREFIX + "[0-9]{3}$", message = "Number must have the prefix " + NUMBER_PREFIX + " and continue with 3 digit characters")
    @Column(nullable = false, length = 5)
    private String number;

    @NotBlank(message = "Title may not be blank")
    @Size(min = 1, max = 64, message = "Title must be between 1 and 64 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,64}$", message = "Title must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Column(nullable = false, length = 64)
    private String title;

    @NotNull(message = "Description may not be null")
    @Size(min = 0, max = 256, message = "Description must be between 0 and 256 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]{0,256}$", message = "Description must be alphanumeric characters and can have white spaces and punctuations")
    @Column(nullable = false, length = 256)
    private String description;

    @NotNull(message = "Estimation may not be null")
    @Size(min = 0, max = 3, message = "Estimation must be between 0 and 3 characters long")
    @Pattern(regexp = "^[A-Za-z0-9]{0,3}$", message = "Estimation must be alphanumeric characters")
    @Column(nullable = false, length = 3)
    private String estimation;

    @NotNull(message = "Priority may not be null")
    @Size(min = 0, max = 32, message = "Priority must be between 0 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 \\-]{0,32}$", message = "Priority must be alphanumeric characters and can have white spaces and hyphens")
    @Column(nullable = false, length = 32)
    private String priority;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "backlog_id", nullable = false, updatable = false)
    private Backlog backlog;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @CreatedBy
    private User createdBy;

    @PastOrPresent(message = "CreatedAt must be in the past or in the present")
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @CreatedBy
    private User updatedBy;

    @PastOrPresent(message = "UpdatedAt must be in the past or in the present")
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Boolean isEstimated() {
        return Objects.nonNull(estimation) && !estimation.isBlank();
    }

    @Override
    public BacklogItem clone() {
        try {
            return (BacklogItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
