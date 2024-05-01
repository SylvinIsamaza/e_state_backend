package com.example.estate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.estate.models.Agent;

import com.example.estate.repository.AgentRepository;
import com.example.estate.repository.UserRepository;
import com.example.estate.utils.Response;

@RestController
public class AgentController {
    private final AgentRepository agentRepository;
    private final UserRepository userRepository;
    
    public AgentController(AgentRepository agentRepository,UserRepository userRepository) {
        this.agentRepository = agentRepository;
        this.userRepository=userRepository;
    }
    @GetMapping("/agents")
    Response<List <Agent>>getAllAgents(){
    Response<List <Agent>> agents=new Response<List<Agent>>(true,agentRepository.findAll());
    return agents;    
    } 
    @GetMapping("/agents/{id}")
    Response<Optional <Agent>> getSingleEmployee(@PathVariable String id){
        Response<Optional <Agent>> agent=new Response<Optional<Agent>>(true, agentRepository.findById(id)) ;
        return agent;
    }
    @PostMapping("/agents/new")
    Response<Agent>  addAgent(@RequestBody Agent agent){
        Agent newAgent=agent;
      
        Response<Agent> response=new Response<Agent>(true, newAgent);
        agentRepository.save(newAgent);
        return response;
    }
    @DeleteMapping("/agents/{id}")
  Response<String> deleteAgentById(@PathVariable String id){
        Response <String> response=new Response<String>(false, "User with id"+ id+"successfully deleted");
        agentRepository.deleteById(id);
        return response;
        

    }

}
