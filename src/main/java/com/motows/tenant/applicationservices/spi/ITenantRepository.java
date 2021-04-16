package com.motows.tenant.applicationservices.spi;

import com.motows.tenant.applicationservices.dto.TenantDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ITenantRepository {
    TenantDTO save(TenantDTO tenantDTO);
    TenantDTO getTenantById(Long tenantId);
    TenantDTO getTenantByName(String tenantName);
    List<TenantDTO> listTenants();
}
