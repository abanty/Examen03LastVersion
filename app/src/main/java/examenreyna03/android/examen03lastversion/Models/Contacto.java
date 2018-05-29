package examenreyna03.android.examen03lastversion.Models;

public class Contacto {

    private int id;
    private String nombre;
    private String telefono;
    private byte[] foto;

    public Contacto() {
    }

    public Contacto(int id, String nombre, String telefono, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.foto = foto;
    }


    public Contacto(int id, String telefono, byte[] foto) {
        this.id = id;
        this.telefono = telefono;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
