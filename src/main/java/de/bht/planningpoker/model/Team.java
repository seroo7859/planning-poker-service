package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Team {

    @Transient
    public static final int MAX_TEAM_SIZE = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Name must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Column(nullable = false, length = 32)
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "team")
    private Session session;

    @EqualsAndHashCode.Exclude
    @NotNull(message = "Users may not be null")
    @Size(min = 1, max = MAX_TEAM_SIZE, message = "A maximum of " + MAX_TEAM_SIZE + " users can be in a team")
    @UniqueElements(message = "Users must not contain duplicates")
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    @JoinTable(
            name = "team_member",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    )
    @OrderColumn(name="list_index")
    private List<@Valid User> members = new ArrayList<>();

    public List<User> getActiveMembers() {
        return members.stream()
                .filter(User::isActive)
                .toList();
    }

    public User getMember(String username) {
        return members
                .stream()
                .filter(member -> username.equalsIgnoreCase(member.getUsername()))
                .findFirst()
                .orElse(null);
    }

    public boolean hasMember(String username) {
        return members
                .stream()
                .anyMatch(member -> username.equalsIgnoreCase(member.getUsername()));
    }

    public void addMember(User member) {
        if (members.size() < MAX_TEAM_SIZE) {
            members.add(member);
            member.setTeam(this);
        }
    }

    public void removeMember(User member) {
        members.remove(member);
        member.setTeam(null);
    }

    public int getSize() {
        return members.size();
    }

    @PrePersist
    private void prePersist() {
        // Set missing back references
        members.forEach(member -> {
            if (Objects.isNull(member.getTeam())) {
                member.setTeam(this);
            }
        });
    }

}
