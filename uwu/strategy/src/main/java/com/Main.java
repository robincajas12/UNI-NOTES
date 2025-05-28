package com;

import com.impl.AnalisisCompleto;
import com.impl.AntivirusCompleto;
import com.models.AnalizarVirus;
import com.models.ETipo;

public class Main {
    public static void main(String[] args) {
        AnalizarVirus analizarVirus = new AnalizarVirus(ETipo.RAPIDO);
        analizarVirus.analizar();
    }
}
