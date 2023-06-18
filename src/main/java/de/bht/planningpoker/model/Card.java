package de.bht.planningpoker.model;

import jakarta.persistence.*;
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
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Label may not be blank")
    @Size(min = 1, max = 32, message = "Value must be between 1 and 32 characters long")
    @Column(nullable = false, length = 32)
    private String label;

    @NotBlank(message = "Value may not be blank")
    @Size(min = 1, max = 3, message = "Value must be between 1 and 3 characters long")
    @Column(nullable = false, length = 3)
    private String value;

    @NotBlank(message = "FrontTextColor may not be blank")
    @Size(min = 3, max = 6, message = "FrontTextColor must be 3 or 6 characters long")
    @Pattern(regexp = "^([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$", message = "FrontTextColor must be hexadecimal color value without hashtag")
    @Column(nullable = false, length = 6)
    private String frontTextColor;

    @NotBlank(message = "BackTextColor may not be blank")
    @Size(min = 3, max = 6, message = "BackTextColor must be 3 or 6 characters long")
    @Pattern(regexp = "^([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$", message = "BackTextColor must be hexadecimal color value without hashtag")
    @Column(nullable = false, length = 6)
    private String backTextColor;

    @NotBlank(message = "BackgroundColor may not be blank")
    @Size(min = 3, max = 6, message = "BackgroundColor must be 3 or 6 characters long")
    @Pattern(regexp = "^([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$", message = "BackgroundColor must be hexadecimal color value without hashtag")
    @Column(nullable = false, length = 6)
    private String backgroundColor;

    @NotBlank(message = "HoverColor may not be blank")
    @Size(min = 3, max = 6, message = "HoverColor must be 3 or 6 characters long")
    @Pattern(regexp = "^([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$", message = "HoverColor must be hexadecimal color value without hashtag")
    @Column(nullable = false, length = 6)
    private String hoverColor;

    @NotNull(message = "CreatedAt may not be null")
    @PastOrPresent(message = "CreatedAt must be in the past or in the present")
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull(message = "UpdatedAt may not be null")
    @PastOrPresent(message = "UpdatedAt must be in the past or in the present")
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
