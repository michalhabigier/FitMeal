package pl.mh.reactapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingClass implements Serializable{

    @JsonIgnore
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;
}
