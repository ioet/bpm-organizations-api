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
    public void getOrganizationResponseOK(){

        String organizationIdToFind = "id";
        Organization organizationFound = mock(Organization.class);
        when(organizationRepository.findById(organizationIdToFind)).thenReturn(Optional.of(organizationFound));

        ResponseEntity<Organization> existingOrganizationResponse = organizationController.getOrganization(organizationIdToFind);

        assertEquals(organizationFound, existingOrganizationResponse.getBody());
        assertEquals(HttpStatus.OK, existingOrganizationResponse.getStatusCode());
    }

    @Test
    public void getOrganizationResponseNotFound(){

        String organizationIdToFind = "id";
        Organization organizationFound = mock(Organization.class);
        when(organizationRepository.findById(organizationIdToFind)).thenReturn(Optional.of(organizationFound));

        ResponseEntity<Organization> existingOrganizationResponse = organizationController.getOrganization(organizationIdToFind);

        assertEquals(organizationFound, existingOrganizationResponse.getBody());
        assertEquals(HttpStatus.OK, existingOrganizationResponse.getStatusCode());
    }

    @Test
    public void deleteOrganizationByIdSucceded(){
        Optional<Organization> organizationFound = Optional.of(mock(Organization.class));

        when(organizationRepository.findById(Mockito.anyString())).thenReturn(organizationFound);
        ResponseEntity<?> deletedOrganizationResponse = organizationController.deleteOrganization(Mockito.anyString());

        assertEquals(HttpStatus.NO_CONTENT, deletedOrganizationResponse.getStatusCode());

        verify(organizationRepository,times(1)).findById(Mockito.anyString());

    }

    @Test
    public void deleteOrganizationByIdNotFound(){
        Optional<Organization> organizationFound = Optional.of(mock(Organization.class));

        ResponseEntity<?> deletedOrganizationResponse = organizationController.deleteOrganization(Mockito.anyString());

        assertEquals(HttpStatus.NOT_FOUND, deletedOrganizationResponse.getStatusCode());

        verify(organizationRepository,times(1)).findById(Mockito.anyString());

    }



    @Test
    public void updateOrganizationSucceded(){

        Optional<Organization> organizationFound = Optional.of(mock(Organization.class));
        Organization organizationToUpdate = mock(Organization.class);

        when(organizationRepository.findById(Mockito.anyString())).thenReturn(organizationFound);
        ResponseEntity<?> updatedOrganizationResponse = organizationController.updateOrganization(Mockito.anyString(), organizationToUpdate);

        assertEquals(HttpStatus.OK, updatedOrganizationResponse.getStatusCode());

        verify(organizationRepository,times(1)).findById(Mockito.anyString());
    }

    @Test
    public void updateOrganizationNotFound(){

        Optional<Organization> organizationFound = Optional.of(mock(Organization.class));
        Organization organizationToUpdate = mock(Organization.class);

        ResponseEntity<?> updatedOrganizationResponse = organizationController.updateOrganization(Mockito.anyString(), organizationToUpdate);

        assertEquals(HttpStatus.NOT_FOUND, updatedOrganizationResponse.getStatusCode());

        verify(organizationRepository,times(1)).findById(Mockito.anyString());
    }
}
