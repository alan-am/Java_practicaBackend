package service;

import dao.IDao;
import model.Paciente;
import org.apache.log4j.Logger;

public class PacienteService {
    private IDao<Paciente> pacienteIDao;
    private static final Logger log = Logger.getLogger(PacienteService.class);

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteIDao.guardar(paciente);
    }
    public Paciente buscarPorID(Integer id){
        return pacienteIDao.buscarPorId(id);
    }
    public Paciente borrarPorId(Integer id){
        return pacienteIDao.borrarPorId(id);
    }

}
