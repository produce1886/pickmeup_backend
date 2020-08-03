package kr.co.pickmeup.domain.tags;

import kr.co.pickmeup.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    private double score;

    private Integer count;
    
    @Builder
    public Tag(String name, double score, Integer count) {
        this.name = name;
        this.score = score;
        this.count = count;
    }

}
