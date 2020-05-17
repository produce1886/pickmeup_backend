package pickmeup.api.entity.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pickmeup.api.entity.common.CommonDateEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Board extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, length = 100)
    private String name;
}
