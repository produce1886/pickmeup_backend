package kr.co.pickmeup.domain.tags;

import kr.co.pickmeup.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Tags extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    private double score;

    private Integer count;
    
    @Builder
    public Tags(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

}
