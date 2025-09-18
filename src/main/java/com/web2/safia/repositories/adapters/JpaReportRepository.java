package com.web2.safia.repositories.adapters;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.web2.safia.models.Report;

public interface JpaReportRepository extends CrudRepository<Report, UUID> {

}
