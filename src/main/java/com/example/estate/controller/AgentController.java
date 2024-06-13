package com.example.estate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.estate.models.Agent;
import com.example.estate.models.User;
import com.example.estate.repository.AgentRepository;
import com.example.estate.repository.UserRepository;
import com.example.estate.utils.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@Tag(name="Agent", description = "Agent APIs")
public class AgentController {
    private final AgentRepository agentRepository;
    private final UserRepository userRepository;

    public AgentController(AgentRepository agentRepository, UserRepository userRepository) {
        this.agentRepository = agentRepository;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Get all agents",
               description = "Retrieve a list of all agents",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                                content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Agent.class)))
               })
    @GetMapping("/agents")
    Response<List<Agent>> getAllAgents() {
        Response<List<Agent>> agents = new Response<List<Agent>>(true, agentRepository.findAll());
        return agents;
    }

    @Operation(summary = "Get a single agent",
               description = "Retrieve a single agent by ID",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Successfully retrieved agent",
                                content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Agent.class))),
                   @ApiResponse(responseCode = "404", description = "Agent not found")
               })
    @GetMapping("/agents/{id}")
    Response<Optional<Agent>> getSingleAgent(@PathVariable String id) {
        Response<Optional<Agent>> agent = new Response<Optional<Agent>>(true, agentRepository.findById(id));
        return agent;
    }

    @Operation(summary = "Create a new agent",
               description = "Add a new agent to the database",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Successfully created agent",
                                content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Agent.class))),
                   @ApiResponse(responseCode = "401", description = "Unauthorized")
               })
    @PostMapping("/agents/new")
    Response<Agent> addAgent(@RequestBody Agent agent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            Response<Agent> newResponse = new Response<Agent>(false, null, "Login to continue");
            return newResponse;
        }
        User user = userRepository.findByEmail(authentication.getPrincipal().toString());

        if (user != null) {
            agent.setUser(user);
            agentRepository.save(agent);
            Response<Agent> response = new Response<Agent>(true, agent);
            return response;
        }
        Response<Agent> newResponse = new Response<Agent>(false, null, "Invalid credentials");
        return newResponse;
    }

    @Operation(summary = "Delete an agent",
               description = "Delete an agent by ID",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Successfully deleted agent",
                                content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = String.class))),
                   @ApiResponse(responseCode = "404", description = "Agent not found")
               })
    @DeleteMapping("/agents/delete/{id}")
    Response<String> deleteAgentById(@PathVariable String id) {
        agentRepository.deleteById(id);
        Response<String> response = new Response<String>(true, "User with id " + id + " successfully deleted");
        return response;
    }

    @Operation(summary = "Update an agent",
               description = "Update an agent by ID",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Successfully updated agent",
                                content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Agent.class))),
                   @ApiResponse(responseCode = "403", description = "Permission denied"),
                   @ApiResponse(responseCode = "404", description = "Agent not found")
               })
    @PostMapping("/agent/update/{id}")
    ResponseEntity<Response<Agent>> updateAgentById(@PathVariable String id, @RequestBody Agent agentPayload)
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            Response<Agent> agent = new Response<Agent>(false, null, "Permission denied");
            return ResponseEntity.status(403).body(agent);
        }
        User user = userRepository.findByEmail(authentication.getPrincipal().toString());
        System.out.println(user.getEmail());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getPrincipal().toString().equals(user.getEmail().toString()));
        if (!authentication.getPrincipal().toString().equals(user.getEmail().toString()) ) {
            Response<Agent> agent = new Response<Agent>(false, null, "Permission denied");
            return ResponseEntity.status(403).body(agent);
        }
        else {
            
            Optional<Agent> agent = agentRepository.findById(id);
            if (!agent.isPresent()) {
                Response<Agent> newResponse = new Response<Agent>(false, null, "Agent not found");
                return ResponseEntity.status(404).body(newResponse);
            } else {
                Agent newAgent = agent.get();
                if (agentPayload.getAbout() != null) {
                    newAgent.setAbout(agentPayload.getAbout());
                }
                if (agentPayload.getPhoneNumber() != null) {
                    newAgent.setPhoneNumber(agentPayload.getPhoneNumber());
                }
                if (agentPayload.getLocation() != null) {
                    newAgent.setLocation(agentPayload.getLocation());
                }
                if (agentPayload.getFacebook() != null) {
                    newAgent.setFacebook(agentPayload.getFacebook());
                }
                if (agentPayload.getInstagram() != null) {
                    newAgent.setInstagram(agentPayload.getInstagram());
                }
                if (agentPayload.getTwitter() != null) {
                    newAgent.setTwitter(agentPayload.getTwitter());
                }
                if (agentPayload.getServiceArea() != null) {
                    newAgent.setServiceArea(agentPayload.getServiceArea());
                }
                if (agentPayload.getAgentLicence() != null) {
                    newAgent.setAgentLicence(agentPayload.getAgentLicence());
                }
                if (agentPayload.getTaxNumber() != null) {
                    newAgent.setTaxNumber(agentPayload.getTaxNumber());
                }
                    if (agentPayload.isVerified()) {
                    newAgent.setVerified(agentPayload.isVerified());
                }
                agentRepository.save(newAgent);
                Response<Agent> response = new Response<Agent>(true, newAgent);
                return ResponseEntity.status(200).body(response);
            }
        }
    }

}
