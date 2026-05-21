package org.neeol.bachpratice.model;


import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Campaigns {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "campaign")
    private List<MessageLogs> messages;

    @PrePersist
    protected void onCreate() {
        if(this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
