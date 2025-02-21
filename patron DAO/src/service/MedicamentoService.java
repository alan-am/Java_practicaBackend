package service;

import dao.IDao;
import model.Medicamento;

import java.util.ArrayList;

//En esta carpeta se maneja la logica de negocio que debe tener cada entidad, en este caso especifico solo funciona como pasamano.

public class MedicamentoService {
    private IDao<Medicamento> medicamentoIDao;

    public MedicamentoService(IDao<Medicamento> medicamentoIDao) {
        this.medicamentoIDao = medicamentoIDao;
    }


    //creamos igual numero de metodos que hay para la entidad en su respectivo Dao(DaoH2Medicamento).

    public Medicamento guardarMedicamento(Medicamento medicamento){
        //validacion , etc, etc y una vez cumplidas las reglas de negocio, procedo a guardar en la DB
        return medicamentoIDao.registrar(medicamento);
    }

    public Medicamento buscarPorId(Integer id){
        return medicamentoIDao.buscarPoId(id);
    }

    public ArrayList<Medicamento> buscarPorCampo(String nombre){
        return medicamentoIDao.buscarPorCampo(nombre);
    }

    public ArrayList<Medicamento> buscarTodos(){
        return  medicamentoIDao.buscarTodos();
    }

}
