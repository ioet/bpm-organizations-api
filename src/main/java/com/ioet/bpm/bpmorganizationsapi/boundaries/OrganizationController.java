package com.ioet.bpm.bpmorganizationsapi.boundaries;


import com.ioet.bpm.bpmorganizationsapi.entities.Organization;
import com.ioet.bpm.bpmorganizationsapi.repositories.OrganizationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/organizations")
@Api(value="/organizations", description="Manage Organizations", produces ="application/json")
public class OrganizationController {

    OrganizationRepository organizationRepository;

    @ApiOperation(value = "Return a list of all organizations", response = Organization.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Organizations succesfully returned")
    })

    @GetMapping(produces = "application/json")
    public ResponseEntity<Iterable> getAllPersons() {
        Iterable<Organization> organizations = this.organizationRepository.findAll();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }



}
