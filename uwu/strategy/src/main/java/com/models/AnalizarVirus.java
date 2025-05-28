package com.models;

import com.impl.AnalisisCompleto;
import com.impl.AntivirusCompleto;
import com.impl.AntivirusRapido;
import com.inter.IAnalize;

public class AnalizarVirus  implements IAnalize {
    private IAnalize analize;
    public AnalizarVirus(ETipo tipo) {
        factory(tipo);
    }

    public void factory(ETipo tipo) {
        switch (tipo) {
            case COMPLETO:
                this.analize = new AntivirusCompleto();
                break;
            case RAPIDO:
                this.analize = new AntivirusRapido();
                break;
        }
    }

    @Override
    public void analizar() {
        analize.analizar();
    }
}


