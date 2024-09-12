package persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Dueño;
import logic.Mascota;
import persistence.exceptions.NonexistentEntityException;

public class ControladoraPersistencia {
    DueñoJpaController dueñoJpa = new DueñoJpaController();
    MascotaJpaController mascotaJpa = new MascotaJpaController();

    public void guardar(Dueño dueño, Mascota mascota) {
        dueñoJpa.create(dueño);
        mascotaJpa.create(mascota);
    }

    public List<Mascota> traerMascotas() {
        return mascotaJpa.findMascotaEntities();
    }

    public void borrarMascotas(int num_cliente) {
        try {
            mascotaJpa.destroy(num_cliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Mascota traerMascota(int num_cliente) {
        return mascotaJpa.findMascota(num_cliente);
    }

    public Dueño traerDueño(int id_dueño) {
        return dueñoJpa.findDueño(id_dueño);
    }
    
    public void editarMascota(Mascota masco) {
        try {
            mascotaJpa.edit(masco);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editarDueño(Dueño dueño) {
        try {
            dueñoJpa.edit(dueño);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
