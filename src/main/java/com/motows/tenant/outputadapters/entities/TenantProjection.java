package com.motows.tenant.outputadapters.entities;

import com.motows.tenant.config.Auditable;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tenant")
@Table(name = "tenant")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantProjection extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenant_id_generator")
    @SequenceGenerator(name="tenant_id_generator", sequenceName = "tenant_id_seq")
    @Column(name = "tenant_id", updatable = false, nullable = false)
    private Long tenantId;
    private String tenantName;
}
