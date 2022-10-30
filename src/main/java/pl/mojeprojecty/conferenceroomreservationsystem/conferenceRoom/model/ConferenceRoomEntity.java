package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConferenceRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "create_date", length = 6, nullable = false)
    private LocalDateTime createDate;
    @Column(name = "update_date", length = 6, nullable = false)
    private LocalDateTime updateDate;

    private String name;
    private String identifier;
    private int floor;
    private boolean availability;
    @Column(name = "number_of_seats",nullable = false)
    private int numberOfSeats;
    @Column(name = "number_of_hammock",nullable = false)
    private int numberOfHammock;

    @ManyToOne
    private OrganizationEntity organization;

    public ConferenceRoomEntity(String name, String identifier, int floor, boolean availability, int numberOfSeats, int numberOfHammock, OrganizationEntity organization) {
        this.name = name;
        this.identifier = identifier;
        this.floor = floor;
        this.availability = availability;
        this.numberOfSeats = numberOfSeats;
        this.numberOfHammock = numberOfHammock;
        this.organization = organization;
    }

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
        this.updateDate = createDate;
    }

    @PreUpdate
    public void updateDate() {
        this.updateDate = LocalDateTime.now();
    }
}
