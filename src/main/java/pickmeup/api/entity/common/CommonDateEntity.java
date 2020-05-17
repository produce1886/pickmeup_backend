package pickmeup.api.entity.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonDateEntity { // 날짜 필드 상속 처리

    @CreatedDate // Entity 생성시 자동으로 날짜세팅
    private LocalDateTime createdAt;

    @LastModifiedDate // Entity 수정시 자동으로 날짜세팅
    private LocalDateTime modifiedAt;
}