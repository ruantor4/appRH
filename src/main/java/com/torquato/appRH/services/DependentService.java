package com.torquato.appRH.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torquato.appRH.models.Dependent;
import com.torquato.appRH.models.Employee;
import com.torquato.appRH.repositories.DependentRepository;
import com.torquato.appRH.repositories.EmployeeRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada aos dependentes.
 * 
 * <p>
 * Esta classe gerencia as operações de persistência dos dependentes, como
 * adicionar,
 * excluir e buscar dependentes associados a um funcionário.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Service
public class DependentService {

    @Autowired
    private DependentRepository dependentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Adiciona um novo dependente a um funcionário, associando-o ao funcionário
     * com o ID fornecido. Verifica se o CPF do dependente já está cadastrado.
     * 
     * @param id        O ID do funcionário ao qual o dependente será associado.
     * @param dependent O dependente a ser adicionado.
     * @return Uma mensagem de sucesso ou erro, indicando o status da operação.
     */
    public String addDependent(Long id, Dependent dependent) {
        // Verifica se já existe um dependente com o mesmo CPF
        if (dependentRepository.findByCpf(dependent.getCpf()) != null) {
            return "CPF duplicado";
        }

        // Busca o funcionário pelo ID usando Optional para evitar NullPointerException
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return "Funcionário não encontrado";
        }

        // Associa o dependente ao funcionário e salva
        Employee employee = optionalEmployee.get();
        dependent.setEmployee(employee);
        dependentRepository.save(dependent);

        return "Dependente adicionado com sucesso";
    }

    /**
     * Exclui um dependente com base no CPF fornecido. Se o dependente não for
     * encontrado,
     * uma exceção será lançada.
     * 
     * @param cpf O CPF do dependente a ser excluído.
     * @return O ID do funcionário ao qual o dependente estava associado, para
     *         redirecionamento.
     * @throws RuntimeException Se o dependente não for encontrado.
     */
    public Long deleteDependent(String cpf) {
        // Busca o dependente pelo CPF
        Dependent dependent = dependentRepository.findByCpf(cpf);

        if (dependent == null) {
            throw new RuntimeException("Dependente não encontrado!");
        }

        // Obtém o ID do funcionário antes de deletar o dependente
        Long employeeId = dependent.getEmployee().getId();

        // Exclui o dependente
        dependentRepository.delete(dependent);

        // Retorna o ID do funcionário para redirecionamento
        return employeeId;
    }

    /**
     * Retorna todos os dependentes de um funcionário específico.
     * 
     * @param employee O funcionário do qual os dependentes serão buscados.
     * @return Uma lista iterável de dependentes associados ao funcionário.
     */
    public Iterable<Dependent> findDependentsByEmployee(Employee employee) {
        return dependentRepository.findByEmployee(employee);
    }
}
