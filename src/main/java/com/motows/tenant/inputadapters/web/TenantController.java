package com.motows.tenant.inputadapters.web;

import com.motows.tenant.applicationservices.api.TenantService;
import com.motows.tenant.applicationservices.dto.TenantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {

    TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public List<TenantDTO> getTenants(){
        return tenantService.listTenants();
    }

    @PostMapping
    public TenantDTO addNewTenant(@RequestBody TenantDTO tenantDTO){
        return tenantService.addTenant(tenantDTO);
    }

    @GetMapping("/{tenantId}/")
        public TenantDTO getTenantById(@PathVariable("tenantId") Long tenantId){
        return tenantService.getTenantById(tenantId);
    }

    @GetMapping("/{tenantName}/")
    public TenantDTO getTenantByName(@PathVariable("tenantName") String tenantName){
        return tenantService.getTenantByName(tenantName);
    }

}
