package com.ioet.bpm.organizations.repositories;



import com.ioet.bpm.organizations.entities.Organization;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


@EnableScan
public interface OrganizationRepository extends CrudRepository <Organization, String> {

}

