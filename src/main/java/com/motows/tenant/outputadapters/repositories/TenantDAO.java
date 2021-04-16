package com.motows.tenant.outputadapters.repositories;

import com.motows.tenant.outputadapters.entities.TenantProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface  TenantDAO extends JpaRepository<TenantProjection, Long> {

}
