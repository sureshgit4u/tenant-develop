package com.motows.tenant.applicationservices.api;

import com.motows.tenant.applicationservices.dto.TenantDTO;
import com.motows.tenant.applicationservices.spi.ITenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TenantService {

    private final Logger log = LoggerFactory.getLogger(TenantService.class);
    private final ITenantRepository tenantRepository;

    @Autowired
    public TenantService(ITenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public TenantDTO addTenant(TenantDTO tenantDTO){
        return this.tenantRepository.save(tenantDTO);
    }

    public TenantDTO getTenantById(Long tenantId){
        return this.tenantRepository.getTenantById(tenantId);
    }

    public TenantDTO getTenantByName(String tenantName) {
        return this.tenantRepository.getTenantByName(tenantName) ;
    }

    public List<TenantDTO> listTenants() {
        return this.tenantRepository.listTenants();
    }
}
