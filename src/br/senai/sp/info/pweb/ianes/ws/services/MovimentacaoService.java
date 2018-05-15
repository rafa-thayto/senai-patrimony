package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.MovimentacaoDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoDAO movimentacaoDAO;

    /**
     * Persists a movimentacao in dabatase
     * @param movimentacao
     * @param brMovimentacao
     * @return
     * @throws ValidationException
     * @throws UnauthorizedException
     */
    public Movimentacao cadastrar(Movimentacao movimentacao, BindingResult brMovimentacao) throws ValidationException, UnauthorizedException {

        // Trata validacoes
        if (brMovimentacao.hasErrors()) {
            throw new ValidationException();
        }

        // Verifica se a categoria já existe
        Movimentacao movimentacaoBuscada = movimentacaoDAO.buscarId(movimentacao.getId());
        if (movimentacaoBuscada != null) {
            throw new ValidationException("A categoria já existe");
        }

        movimentacaoDAO.persistir(movimentacao);
        return movimentacao;

    }

    /**
     * Search by ID a movimentacao in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public Movimentacao buscarPorId(Long id) throws EntityNotFoundException, UnauthorizedException {

        Movimentacao movimentacaoBuscada = movimentacaoDAO.buscarId(id);
        if (movimentacaoBuscada == null) {
            throw new EntityNotFoundException();
        }

        return movimentacaoBuscada;
    }

    /**
     * Search all movimentacao in database
     * @return
     * @throws UnauthorizedException
     */
    public List<Movimentacao> buscarTodos() throws UnauthorizedException {
        return movimentacaoDAO.buscarTodos();
    }

    /**
     * Delete a movimentacao in database
     * @param id
     * @throws EntityNotFoundException
     * @throws UnauthorizedException
     */
    public void deletar(Long id) throws EntityNotFoundException, UnauthorizedException {

        Movimentacao movimentacaoBuscada = movimentacaoDAO.buscarId(id);
        if (movimentacaoBuscada == null) {
            throw new EntityNotFoundException();
        }

        movimentacaoDAO.deletar(movimentacaoBuscada);
    }

    /**
     * Update movimentacao in database
     * @param movimentacao
     * @throws UnauthorizedException
     */
    public void alterar(Movimentacao movimentacao) throws UnauthorizedException {
        movimentacaoDAO.alterar(movimentacao);
    }


}
