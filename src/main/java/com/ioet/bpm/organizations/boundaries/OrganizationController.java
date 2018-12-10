package com.ioet.bpm.organizations.boundaries;

import com.ioet.bpm.organizations.domain.Organization;
import com.ioet.bpm.organizations.repositories.OrganizationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/organizations")
@Api(value = "/organizations", description = "Manage Organizations", produces = "application/json")
public class OrganizationController {

    private final OrganizationRepository organizationRepository;


    @ApiOperation(value = "Return a list of all organizations", response = Organization.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Organizations succesfully returned")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<Iterable> getAllOrganizations() {
        Iterable<Organization> organizations = this.organizationRepository.findAll();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }

    @ApiOperation(value = "Return one organization by id", response = Organization.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Organization returned")
    })
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Organization> getOrganization(@PathVariable(value = "id") String organizationId) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        return organizationOptional.map(
                organization -> new ResponseEntity<>(organization, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ApiOperation(value = "Create new Organization", response = Organization.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Organization successfully created ")
    })
    @PostMapping(produces = "application/json")
    public ResponseEntity<Organization> createOrganization(@Valid @RequestBody Organization organization) {
        Organization organizationCreated = organizationRepository.save(organization);
        return new ResponseEntity<>(organizationCreated, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Delete an organization")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Organization successfully deleted"),
            @ApiResponse(code = 404, message = "Organization to delete not found")
    })
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Organization> deleteOrganization(@PathVariable(value = "id") String organizationId) {
        Optional<Organization> foundOrganization = organizationRepository.findById(organizationId);
        if (foundOrganization.isPresent()) {
            organizationRepository.delete(foundOrganization.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Update an Organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Organization successfully updated"),
            @ApiResponse(code = 404, message = "Organization to delete not found")
    })
    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Organization> updateOrganization(@PathVariable(value = "id") String organizationId,
                                                           @Valid @RequestBody Organization organization) {
        Optional<Organization> foundOrganization = organizationRepository.findById(organizationId);
        if (foundOrganization.isPresent()) {
            Organization updatedOrganization = organizationRepository.save(organization);
            return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
