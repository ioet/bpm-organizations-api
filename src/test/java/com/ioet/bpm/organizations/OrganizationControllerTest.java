package com.ioet.bpm.organizations;

import com.ioet.bpm.organizations.boundaries.OrganizationController;
import com.ioet.bpm.organizations.domain.Organization;
import com.ioet.bpm.organizations.repositories.OrganizationRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationControllerTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationController organizationController;

    @Test
    public void createOrganizationResponseSuccess() {
        Organization organizationCreated = mock(Organization.class);
        Organization organizationToCreate = mock(Organization.class);

        ResponseEntity<Organization> organizationResponse = organizationController.createOrganization(organizationToCreate);

        verify(organizationRepository).save(organizationToCreate);
        assertEquals(HttpStatus.CREATED, organizationResponse.getStatusCode());

    }

    @Test
    public void testGetAllOrganizationsUseTheRepository() {

        ResponseEntity<Iterable> organizations = organizationController.getAllOrganizations();

        assertEquals(HttpStatus.OK, organizations.getStatusCode());

        verify(organizationRepository).findAll();
    }

    @Test
    public void testGetOrganizationByIdReturnAOrganization() {
        Organization organizationCreated = mock(Organization.class);
        Organization organizationToCreate = new Organization();
        organizationToCreate.setId("id");
        organizationToCreate.setName("ioet");
        when(organizationRepository.save(organizationToCreate)).thenReturn(organizationCreated);

        ResponseEntity<Organization> organizationCreatedResponse = organizationController.createOrganization(organizationToCreate);

        assertEquals(organizationCreated, organizationCreatedResponse.getBody());
        assertEquals(HttpStatus.CREATED, organizationCreatedResponse.getStatusCode());
        verify(organizationRepository).save(organizationToCreate);
    }

    @Test
    public void whenAOrganizationIsFoundThenReturn200() {
        Organization organizationFound = mock(Organization.class);
        String organizationFoundId = "id";
        when(organizationRepository.findById(organizationFoundId)).thenReturn(Optional.of(organizationFound));

        ResponseEntity response = organizationController.getOrganization(organizationFoundId);

        verify(organizationRepository).findById(organizationFoundId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenAOrganizationIsNotFoundThenReturn400() {
        String id = "id";
        when(organizationRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity response = organizationController.getOrganization(id);

        verify(organizationRepository).findById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenANotFoundOrganizationIsDeletedThenReturn404() {
        String id = "id";
        when(organizationRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity deleteResponse = organizationController.deleteOrganization(id);

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }

    @Test
    public void whenAOrganizationIsDeletedAndIsFoundThenReturnEmpty(){
        String organizationIdToDelete = "id";
        Organization organizationToDelete = mock(Organization.class);
        when(organizationRepository.findById(organizationIdToDelete)).thenReturn(Optional.of(organizationToDelete));

        ResponseEntity<?> deletedPersonResponse = organizationController.deleteOrganization(organizationIdToDelete);

        assertNull(deletedPersonResponse.getBody());
        assertEquals(HttpStatus.NO_CONTENT, deletedPersonResponse.getStatusCode());
        verify(organizationRepository, times(1)).delete(organizationToDelete);
    }

    @Test
    public void whenAOrganizationIsUpdatedThenReturn200() {
        Organization organizationToUpdate = mock(Organization.class);
        Organization organizationUpdated = mock(Organization.class);
        Optional<Organization> organizationFound = Optional.of(mock(Organization.class));
        String organizationFoundId= "id";
        when(organizationRepository.findById(organizationFoundId)).thenReturn(organizationFound);
        when(organizationRepository.save(organizationToUpdate)).thenReturn(organizationUpdated);

        ResponseEntity<Organization> updatedOrganizationResponse = organizationController.updateOrganization(organizationFoundId, organizationToUpdate);

        assertEquals(HttpStatus.OK, updatedOrganizationResponse.getStatusCode());
        verify(organizationRepository).save(organizationToUpdate);
    }

    @Test
    public void whenANotFoundOrganizationIsUpdatedThenReturn400() {
        Organization organizationToUpdate = mock(Organization.class);
        when(organizationRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> updatedOrganizationResponse = organizationController.updateOrganization(Mockito.anyString(), organizationToUpdate);

        assertEquals(HttpStatus.NOT_FOUND, updatedOrganizationResponse.getStatusCode());
        verify(organizationRepository).findById(Mockito.anyString());
    }
}
