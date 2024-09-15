package com.example.wallet.model.persistence;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.wallet.model.Persistent;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Table(name = "tenants")
@Accessors(fluent = false, chain = true)
public class TenantPersistent implements Persistent {
    
    @Id
    private Long id;
    
    private String firstname;
    
    private String lastname;

    private String nickname;
    
    @Column("PWD")
    private String password;

    private String email;

    @Column("CREATEDAT")
    private OffsetDateTime createdAt;
    @Column("UPDATEDAT")
    private OffsetDateTime updatedAt;
}
