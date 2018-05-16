package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.AmbienteDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Ambiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class AmbienteService {

    @Autowired
    private AmbienteDAO ambienteDAO;

    /**
     * Persists a Ambiente in database
     * @param ambiente
     * @param brambiente
     * @return
     * @throws ValidationException
     * @throws UnauthorizedException
     */
    public Ambiente cadastrar(Ambiente ambiente, BindingResult brambiente) throws ValidationException, UnauthorizedException {

        // Trata validacoes
        if (brambiente.hasErrors()) {
            throw new ValidationException();
        }

        // Verifica se a ambiente já existe
        Ambiente ambienteBuscada = ambienteDAO.buscarId(ambiente.getId());
        if (ambienteBuscada != null) {
            throw new ValidationException("A ambiente já existe");
        }

        ambienteDAO.persistir(ambiente);
        return ambiente;

    }

    /**
     * Search by ID a Ambiente in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public Ambiente buscarPorId(Long id) throws EntityNotFoundException, UnauthorizedException {

        Ambiente ambienteBuscado = ambienteDAO.buscarId(id);
        if (ambienteBuscado == null) {
            throw new EntityNotFoundException();
        }

        return ambienteBuscado;
    }

    /**
     * Search all Ambiente in database
     * @return
     * @throws UnauthorizedException
     */
    public List<Ambiente> buscarTodos() throws UnauthorizedException {
        return ambienteDAO.buscarTodos();
    }

    /**
     * Delete a Ambiente in database
     * @param id
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public void deletar(Long id) throws EntityNotFoundException, UnauthorizedException {

        Ambiente ambienteBuscada = ambienteDAO.buscarId(id);
        if (ambienteBuscada == null) {
            throw new EntityNotFoundException();
        }

        ambienteDAO.deletar(ambienteBuscada);
    }

    /**
     * Update Ambiente in database
     * @param ambiente
     * @throws UnauthorizedException
     */
    public void alterar(Ambiente ambiente) throws UnauthorizedException {
        ambienteDAO.alterar(ambiente);
    }

}
