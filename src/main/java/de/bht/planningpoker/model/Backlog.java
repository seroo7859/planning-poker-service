package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Backlog {

    @Transient
    public static final int MAX_BACKLOG_SIZE = 1000;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "backlog")
    private Session session;

    @Size(min = 0, max = MAX_BACKLOG_SIZE, message = "A maximum of " + MAX_BACKLOG_SIZE + " backlog items can be in a backlog")
    @UniqueElements(message = "Items must not contain duplicates")
    @OneToMany(mappedBy = "backlog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name="list_index")
    private List<@Valid BacklogItem> items = new ArrayList<>();

    @NotNull(message = "CreatedAt may not be null")
    @PastOrPresent(message = "CreatedAt must be in the past or in the present")
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull(message = "UpdatedAt may not be null")
    @PastOrPresent(message = "UpdatedAt must be in the past or in the present")
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public List<BacklogItem> getEstimatedItems() {
        return items.stream()
                .filter(BacklogItem::isEstimated)
                .toList();
    }

    public List<BacklogItem> getNotEstimatedItems() {
        return items.stream()
                .filter(backlogItem -> !backlogItem.isEstimated())
                .toList();
    }

    public int getItemIndex(String number) {
        if (Objects.isNull(number)) {
            return -1;
        }
        return IntStream.range(0, items.size())
                .filter(index -> number.equals(items.get(index).getNumber()))
                .findFirst()
                .orElse(-1);
    }

    public BacklogItem getItem(String number) {
        return items
                .stream()
                .filter(item -> number.equals(item.getNumber()))
                .findFirst()
                .orElse(null);
    }

    public void setItem(String number, BacklogItem newItem) {
        if (Objects.isNull(number) || Objects.isNull(newItem)) {
            return;
        }
        BacklogItem itemFound = getItem(number);
        if(Objects.nonNull(itemFound)) {
            itemFound.setTitle(newItem.getTitle());
            itemFound.setDescription(newItem.getDescription());
            itemFound.setEstimation(newItem.getEstimation());
            itemFound.setPriority(newItem.getPriority());
        }
    }

    public boolean hasItem(String number) {
        if (Objects.isNull(number)) {
            return false;
        }
        return items
                .stream()
                .anyMatch(item -> number.equals(item.getNumber()));
    }

    private String getNextNumber() {
        return IntStream.range(0, items.size())
                .mapToObj(value -> String.format("US%03d", value + 1))
                .filter(number -> !hasItem(number))
                .findFirst()
                .orElse("US001");
    }

    public void addItems(List<BacklogItem> items) {
        items.forEach(this::addItem);
    }

    public void addItem(BacklogItem item) {
        if (items.size() < MAX_BACKLOG_SIZE) {
            items.add(item);

            if (Objects.isNull(item.getNumber())) {
                item.setNumber(getNextNumber());
            }
            item.setBacklog(this);
        }
    }

    public void removeItem(BacklogItem item) {
        items.remove(item);
        item.setBacklog(null);
    }

    public void clear() {
        items.clear();
    }

    public int getSize() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.size() == 0;
    }

    public String getSessionId() {
        return session.getPublicId();
    }

    @PrePersist
    private void setMissingBackReferences() {
        items.forEach(item -> {
            if (Objects.isNull(item.getBacklog())) {
                item.setBacklog(this);
            }
        });
    }

}
