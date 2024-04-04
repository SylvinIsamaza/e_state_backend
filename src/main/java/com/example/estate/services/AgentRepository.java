package com.example.estate.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estate.models.Agent;

public interface AgentRepository extends JpaRepository<Agent,String> {

}
