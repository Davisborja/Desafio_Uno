/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import io.swagger.model.Periodo;
import io.swagger.modelExternos.PeriodoPrevired;
import java.util.ArrayList;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

/**
 *
 * @author Analista
 */
public class ControlerPeriodo {

    public Periodo periodosFaltantes(PeriodoPrevired periodo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Periodo periodo2 = new Periodo();
        //se asignan los datos recibidos a nuestro objeto
        periodo2.setId(periodo.getId());
        periodo2.setFechaCreacion(LocalDate.parse(periodo.getFechaCreacion(), formatter));
        periodo2.setFechaFin(LocalDate.parse(periodo.getFechaFin(), formatter));
        periodo2.setFechas(new ArrayList<LocalDate>());
        periodo2.setFechasFaltantes(new ArrayList<LocalDate>());

        for (String fecha : periodo.getFechas()) {
            periodo2.getFechas().add(LocalDate.parse(fecha, formatter));
        }
        //Se crean variables para generar la logica de los periodos faltantes se asignan a int para mayor eficacia
        int fechaInicio = Integer.parseInt(periodo.getFechaCreacion().replaceAll("-", "").substring(0, 6));
        int fechaTermino = Integer.parseInt(periodo.getFechaFin().replaceAll("-", "").substring(0, 6));

        int indice = 0;
        int indiceMes = Integer.parseInt(periodo.getFechaCreacion().replaceAll("-", "").substring(4, 6));

        int auxTermino = fechaTermino;
        if (Integer.parseInt(String.valueOf(auxTermino).substring(4, 6)) == 12) {
            auxTermino = fechaTermino + 89;
        } else {
            auxTermino++;
        }

        int fechaProcesada = fechaInicio;
        boolean seAcaba = false;
        int fechaRecibida = 0;
        //se itera validando y escribiendo los periodos faltantes, la cantidad de iteraciones va a ser igual a la de periodo faltantes
        while (fechaProcesada < auxTermino) {
            if (!seAcaba) {
                fechaRecibida = Integer.parseInt(periodo.getFechas().get(indice).replaceAll("-", "").substring(0, 6));
            } else {
                fechaRecibida = auxTermino;
            }
            if (fechaProcesada < fechaRecibida) {
                fechaProcesada = (Integer.parseInt(String.valueOf(fechaProcesada).substring(0, 4)) * 100) + indiceMes;
                String fechaEnviada = new StringBuilder(String.valueOf(fechaProcesada)).insert(4, '-').append("-01").toString();
                periodo2.getFechasFaltantes().add(LocalDate.parse(fechaEnviada, formatter));
                if (indiceMes == 12) {
                    indiceMes = 1;
                    fechaProcesada += +89;
                } else {
                    indiceMes++;
                    fechaProcesada++;
                }
            } else {
                indice++;
                if (indice >= periodo.getFechas().size()) {
                    seAcaba = true;
                }
                fechaProcesada++;
                indiceMes++;
                if (Integer.parseInt(String.valueOf(fechaProcesada).substring(4, 6)) == 13) {
                    indiceMes = 1;
                    fechaProcesada += +88;
                }
            }
        }
        return periodo2;
    }
}
