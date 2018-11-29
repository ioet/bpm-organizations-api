package com.ioet.bpm.bpmorganizationsapi.repositories;



import com.ioet.bpm.bpmorganizationsapi.entities.Organization;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


@EnableScan
public interface OrganizationRepository extends CrudRepository <Organization, String> {

    public List<Organization> findAllByUid();

    public  Optional<Organization> findAllByUid(String uid);


}

