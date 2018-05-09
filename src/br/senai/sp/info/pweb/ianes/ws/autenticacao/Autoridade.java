package br.senai.sp.info.pweb.ianes.ws.autenticacao;

import br.senai.sp.info.pweb.ianes.ws.models.TiposUsuario;

public enum Autoridade {

    COMUM(0),
    ADMINISTRADOR(1);

    private final Integer level;

    Autoridade(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public static Autoridade parseTipoUsuario(TiposUsuario tu) {
        switch(tu) {
            case COMUM:
                return COMUM;
            case ADMINISTRADOR:
                return ADMINISTRADOR;
        }
        return COMUM;
    }

}
