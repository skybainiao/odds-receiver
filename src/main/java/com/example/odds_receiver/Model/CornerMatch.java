package com.example.odds_receiver.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "corner_matches", schema = "system")
public class CornerMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private String leagueName;
    private LocalDateTime matchTime;
    private String homeTeam;
    private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;
    private LocalDateTime insertedAt = LocalDateTime.now();



    @OneToMany(mappedBy = "cornerMatch", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<CornerOdd> odds;



    // Getters and Setters
}
