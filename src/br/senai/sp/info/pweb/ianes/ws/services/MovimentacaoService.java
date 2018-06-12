package br.senai.sp.info.pweb.ianes.ws.services;

import br.senai.sp.info.pweb.ianes.ws.dao.MovimentacaoDAO;
import br.senai.sp.info.pweb.ianes.ws.exceptions.EntityNotFoundException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.UnauthorizedException;
import br.senai.sp.info.pweb.ianes.ws.exceptions.ValidationException;
import br.senai.sp.info.pweb.ianes.ws.models.ItemPatrimonio;
import br.senai.sp.info.pweb.ianes.ws.models.Movimentacao;
import br.senai.sp.info.pweb.ianes.ws.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoDAO movimentacaoDAO;

    @Autowired
    private ItemPatrimonioService itemPatrimonioService;

    /**
     * Search by ID a movimentacao in database
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    public Movimentacao buscarPorId(Long id) throws EntityNotFoundException {

        Movimentacao movimentacaoBuscada = movimentacaoDAO.buscarId(id);
        if (movimentacaoBuscada == null) {
            throw new EntityNotFoundException();
        }

        return movimentacaoBuscada;
    }

    /**
     * Search all movimentacao in database
     * @return
     */
    public List<Movimentacao> buscarTodos() {
        return movimentacaoDAO.buscarTodos();
    }

    /**
     * Delete a movimentacao in database
     * @param id
     * @throws EntityNotFoundException
     */
    public void deletar(Long id) throws EntityNotFoundException {

        Movimentacao movimentacaoBuscada = movimentacaoDAO.buscarId(id);
        if (movimentacaoBuscada == null) {
            throw new EntityNotFoundException();
        }

        movimentacaoDAO.deletar(movimentacaoBuscada);
    }

    public Movimentacao movimentar(Movimentacao movimentacao, BindingResult bindingResult, Long itemId) throws ValidationException, EntityNotFoundException {

        ItemPatrimonio itemPatrimonio = itemPatrimonioService.buscarPorId(itemId);

        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        if (itemPatrimonio.getAmbienteAtual().getId() == movimentacao.getDestino().getId()) {
            bindingResult.addError(new FieldError("movimentacao","ambiente","O ambiente destino não pode ser igual ao ambiente atual"));
            throw new ValidationException();
        }

        System.out.println("Movimentacao: " + movimentacao.toString());
        System.out.println("Movimentacao ITEM: " + itemPatrimonio.toString() + " ID: " + itemId);

        movimentacao.setOrigem(itemPatrimonio.getAmbienteAtual());
        movimentacao.setDestino(movimentacao.getDestino());
        itemPatrimonioService.alterarAmbiente(movimentacao.getItemPatrimonio().getId());

        // Settando usuário
        Usuario usuarioBuscado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        movimentacao.setUsuario(usuarioBuscado);

        // Settando item que vai ser movimentado
        movimentacao.setItemPatrimonio(itemPatrimonio);

        // Data da movimentacao
        movimentacao.setData_movimentacao(new Date());


        movimentacaoDAO.persistir(movimentacao);

        return movimentacao;

    }
}
