package pblweek2.megazine.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {
    @CreatedDate
    public LocalDateTime createdAt;

    @LastModifiedBy
    public LocalDateTime modifiedAt;
}
