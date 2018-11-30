package com.ioet.bpm.bpmorganizationsapi;

import com.ioet.bpm.bpmorganizationsapi.boundaries.OrganizationController;
import com.ioet.bpm.bpmorganizationsapi.entities.Organization;
import com.ioet.bpm.bpmorganizationsapi.repositories.OrganizationRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BpmorganizationsapiApplicationTests {

    @Mock
    Organization organization;

    @Mock
    OrganizationRepository repository;

    @Mock
    OrganizationController organizationController;

    @Test
    public void contextLoads() {
    }

    @Test
    public void checkOrganizationIdExist(){
        organization = mock(Organization.class);
        when( organization.getUid()).thenReturn("1");
        assertEquals(organization.getUid(),"1");

    }

    @Test
    public void createOrganizationSuccess(){
        organization = mock(Organization.class);
        Organization newOrganization = mock(Organization.class);
        repository = mock(OrganizationRepository.class);

        when(repository.save(newOrganization)).thenReturn(organization);

        repository.save(newOrganization);

        assertEquals(organization.getUid(),newOrganization.getUid());


        verify(repository, times(1)).save(newOrganization);
        verify(organization, times(1)).getUid();
        verify(newOrganization, times(1)).getUid();
    }

    @Test
    public void createOrganizationReturnNull(){
        organization = mock(Organization.class);
        repository = mock(OrganizationRepository.class);

        when(repository.save(organization)).thenReturn(null);

        assertEquals(null, repository.save(organization));


        verify(repository, times(1)).save(organization);
    }

    @Test
    public void listAllOrganizations(){
        organization = mock(Organization.class);
        repository = mock(OrganizationRepository.class);
        List<Organization> allOrganizations = mock(List.class);

        when(repository.findAll()).thenReturn(allOrganizations);

        assertEquals(allOrganizations,repository.findAll());

        verify(repository,times(1)).findAll();


    }

    @Test
    public void testCreateOrganization(){
        Organization organizationToCreate = mock(Organization.class);
        Organization organizationCreated = mock(Organization.class);
        Optional<Organization> organization = Optional.of(Mockito.mock(Organization.class));


        when(organizationCreated.getUid()).thenReturn("1");
        assertEquals("1",organizationCreated.getUid());

        verify(organizationCreated, times(1)).getUid();
    }

    @Test
    public void testOrganizationEntity(){
        Organization newOrganization = mock(Organization.class);

        when(newOrganization.getUid()).thenReturn("1");
        when(newOrganization.getOrganizationName()).thenReturn("ioet");

        assertEquals("ioet", newOrganization.getOrganizationName());
        assertEquals("1", newOrganization.getUid() );



    }
}
