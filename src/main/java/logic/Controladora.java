package logic;
import java.util.List;
import persistence.ControladoraPersistencia;
public class Controladora {
    ControladoraPersistencia controlPersis = new ControladoraPersistencia(); //instancia de controladora de persistencia

    public void guardar(String nombreDueño, String celDueño, String direccionDueño, String nombreMasco, String raza, String color, String observaciones, String alergico, String atenEspecial) {
        Dueño dueño = new Dueño();
        dueño.setNombre(nombreDueño);
        dueño.setCelular(celDueño);
        dueño.setDireccion(direccionDueño);
        
        Mascota mascota = new Mascota();
        mascota.setNombre_perro(nombreMasco);
        mascota.setRaza(raza);
        mascota.setColor(color);
        mascota.setAlergico(alergico);
        mascota.setAtencion_especial(atenEspecial);
        mascota.setObservaciones(observaciones);
        mascota.setUnDueño(dueño);
        
        controlPersis.guardar(dueño,mascota);
        
    }

    public List<Mascota> traerMascotas() {
        return controlPersis.traerMascotas();
    }

    public void borrarCliente(int num_cliente) {
        controlPersis.borrarMascotas(num_cliente);
    }

    public Mascota traerMascota(int num_cliente) {
        return controlPersis.traerMascota(num_cliente);
    }

    public void editarMascota(Mascota masco, String nombreDueño, String celDueño, String direccionDueño, String nombreMasco, String raza, String color, String observaciones, String alergico, String atenEspecial) {
       masco.setNombre_perro(nombreMasco);
       masco.setRaza(raza);
       masco.setColor(color);
       masco.setObservaciones(observaciones);
       masco.setAlergico(alergico);
       masco.setAtencion_especial(atenEspecial);
       
       //modificar mascota
       controlPersis.editarMascota(masco);
       
       Dueño dueño = this.buscarDueño(masco.getUnDueño().getId_dueño());
       dueño.setNombre(nombreDueño);
       dueño.setCelular(celDueño);
       dueño.setDireccion(direccionDueño);
       
       this.editarDueño(dueño);
    }
    private Dueño buscarDueño(int id_dueño) {
        return controlPersis.traerDueño(id_dueño);
    }

    private void editarDueño(Dueño dueño) {
        controlPersis.editarDueño(dueño);
    }
}
