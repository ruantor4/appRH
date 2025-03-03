package com.torquato.appRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.torquato.appRH.models.Dependent;
import com.torquato.appRH.models.Employee;
import com.torquato.appRH.services.DependentService;
import com.torquato.appRH.services.EmployeeService;

public class DependentController {

    @Autowired
    private DependentService dependentService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/dependentes/{id}")
    public ModelAndView dependentes(@PathVariable long id) {
        Employee employee = employeeService.getEmployeeById(id);
        ModelAndView mv = new ModelAndView("/funcionario/dependentes");
        mv.addObject("employees", employee);

        Iterable<Dependent> dependents = dependentService.findDependentsByEmployee(employee);
        mv.addObject("dependents", dependents);
        return mv;
    }

    
}
    

