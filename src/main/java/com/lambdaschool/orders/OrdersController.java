package com.lambdaschool.orders;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.AgentRepository;
import com.lambdaschool.orders.repositories.CustomerRepository;
import com.lambdaschool.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersController {
  @Autowired
  AgentRepository agentRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  OrderRepository orderRepository;

  @GetMapping("customer/order")
  public List<Customer> allCustomers() {
    List<Customer> customers = customerRepository.findAll();

    customers = customers.stream()
      .map(customer -> {
        Set<Order> orders = new HashSet<>(
          orderRepository.customerOrders()
        );
        customer.setOrders(orders);
        return customer;
      })
      .collect(Collectors.toList());

    return customers;
  }

  @GetMapping("agents")
  public List<Agent> allAgents() {
    List<Agent> agents = agentRepository.findAll();

    agents = agents.stream()
      .map(agent -> {
        Set<Customer> customers = new HashSet<>(
          customerRepository
          .agentCustomers()
        );
        agent.setCustomers(customers);
        return agent;
      })
      .collect(Collectors.toList());

    return agents;
  }
}