package com.motows.tenant.outputadapters.entities;

import com.motows.tenant.config.Auditable;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

import javax.persistence.*;


@Entity
@Table(name = "vendor")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Vendor {
@Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "Vendor_Code", updatable = false, nullable = false)
   private Long Vendor_Code;
   private String VendorName;
   private Long CompanyCode;
   private String ItemName;
   private double Quantity;
   private String Itemcode;
  private BigDecimal amount;
   
}