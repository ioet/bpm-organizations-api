package com.ioet.bpm.organizations;

import com.ioet.bpm.organizations.boundaries.OrganizationController;
import com.ioet.bpm.organizations.entities.Organization;
import com.ioet.bpm.organizations.repositories.OrganizationRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BpmorganizationsapiApplicationTests {

    @Mock
    Organization organization;

    @Mock
    OrganizationRepository organizationRepository;

    @InjectMocks
    OrganizationController organizationController;


    @Test
    public void createOrganizationResponseSuccess(){
        Organization organizationCreated = mock(Organization.class);
        Organization organizationToCreate = mock(Organization.class);


        when(organizationRepository.save(organizationToCreate)).thenReturn(organizationCreated);
        organizationRepository.save(organizationToCreate);

        ResponseEntity<?> organizationResponse= organizationController.createOrganization(organizationCreated);

        assertEquals(HttpStatus.CREATED, organizationResponse.getStatusCode());

    }


    @Test
    public void getAllOrganizationsResponseOK(){
        ResponseEntity<Iterable> organizations =  organizationController.getAllOrganizations();

        assertEquals(HttpStatus.OK, organizations.getStatusCode());

        Mockito.verify(organizationRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void deleteOrganizationByIdSucceded(){
        Optional<Organization> organizationFound = Optional.of(mock(Organization.class));

        when(organizationRepository.findById(Mockito.anyString())).thenReturn(organizationFound);
        ResponseEntity<?> deletedOrganizationResponse = organizationController.deleteOrganization(Mockito.anyString());

        assertEquals(HttpStatus.NO_CONTENT, deletedOrganizationResponse.getStatusCode());

        verify(organizationRepository,times(1)).findById(Mockito.anyString());

    }





}
