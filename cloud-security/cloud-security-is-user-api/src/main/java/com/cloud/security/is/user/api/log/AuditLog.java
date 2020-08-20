package com.cloud.security.is.user.api.log;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * @apiNode:
 * @since 2020/1/15
 * @author : weizc 
 */
@Accessors(chain = true)
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {


    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;


    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;


    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyDate;




    private String method;

    private String path;

    @Column(length = 20)
    private Integer status;

    @CreatedBy
    private String username;
}
