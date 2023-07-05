package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
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
public class DiscussionPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "discussion_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Discussion discussion;

    @NotBlank(message = "Content may not be blank")
    @Size(min = 1, max = 512, message = "Content must be between 1 and 512 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~\\r\\n\\p{So}]{1,512}$", message = "Content must be alphanumeric characters and can have white spaces, punctuations, linebreaks and other symbols")
    @Column(nullable = false, length = 512)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @CreatedBy
    private User author;

    @PastOrPresent(message = "CreatedAt must be in the past or in the present")
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @PastOrPresent(message = "UpdatedAt must be in the past or in the present")
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
