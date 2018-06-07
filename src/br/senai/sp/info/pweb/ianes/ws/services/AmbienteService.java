package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.AmbienteDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Ambiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
     */
    public Ambiente cadastrar(Ambiente ambiente, BindingResult brambiente) throws ValidationException {

        // Trata validacoes
        if (brambiente.hasErrors()) {
            throw new ValidationException();
        }

        // Verifica se a ambiente já existe
        if (ambienteDAO.buscarPorNome(ambiente.getNome()) != null) {
            brambiente.addError(new FieldError("ambiente", "nome", "O nome ja esta em uso"));
            throw new ValidationException();
        }

        ambienteDAO.persistir(ambiente);
        return ambiente;

    }

    /**
     * Search by ID a Ambiente in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    public Ambiente buscarPorId(Long id) throws EntityNotFoundException {

        Ambiente ambienteBuscado = ambienteDAO.buscarId(id);
        if (ambienteBuscado == null) {
            throw new EntityNotFoundException();
        }

        return ambienteBuscado;
    }

    /**
     * Search all Ambiente in database
     * @return
     */
    public List<Ambiente> buscarTodos() {
        return ambienteDAO.buscarTodos();
    }

    /**
     * Delete a Ambiente in database
     * @param id
     * @throws EntityNotFoundException
     */
    public void deletar(Long id) throws EntityNotFoundException {
        ambienteDAO.deletar(buscarPorId(id));
    }

    /**
     * Update ambiente in database
     * @param id
     * @param ambiente
     * @param bindingResult
     * @return
     * @throws EntityNotFoundException
     * @throws ValidationException
     */
    public Ambiente alterar(Long id, Ambiente ambiente, BindingResult bindingResult) throws EntityNotFoundException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Ambiente ambienteBuscado = buscarPorId(id);
        ambienteBuscado.setNome(ambiente.getNome());

        ambienteDAO.alterar(ambienteBuscado);

        return  ambienteBuscado;

    }
}
