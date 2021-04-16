package com.motows.tenant.outputadapters.repositories;

import com.motows.tenant.applicationservices.dto.TenantDTO;
import com.motows.tenant.applicationservices.spi.ITenantRepository;
import com.motows.tenant.outputadapters.entities.TenantProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TenantRepository implements ITenantRepository {

    TenantDAO tenantDAO;

    @Autowired
    public TenantRepository(TenantDAO tenantDAO) {
        this.tenantDAO = tenantDAO;
    }

    @Override
    public TenantDTO save(TenantDTO tenantDTO) {
        TenantProjection tenantProjection = TenantProjection.builder()
                                                            .tenantId(tenantDTO.getTenantId())
                                                            .tenantName(tenantDTO.getTenantName())
                                                            .build();
        TenantProjection tenantProjectionNew = tenantDAO.save(tenantProjection);
        return TenantDTO.builder()
                .tenantId(tenantProjectionNew.getTenantId())
                .tenantName(tenantProjectionNew.getTenantName())
                .build();
    }

    @Override
    public TenantDTO getTenantById(Long tenantId) {
        return null;

    }

    @Override
    public TenantDTO getTenantByName(String tenantName) {
        return null;
    }

    @Override
    public List<TenantDTO> listTenants() {
        return null;
    }


}

