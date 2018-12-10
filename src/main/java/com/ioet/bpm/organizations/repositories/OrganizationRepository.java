package com.ioet.bpm.organizations.repositories;


import com.ioet.bpm.organizations.domain.Organization;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


@EnableScan
public interface OrganizationRepository extends CrudRepository<Organization, String> {

}

