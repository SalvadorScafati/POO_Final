package battleship.modelo;


public class Barco {

	private  int tamaño=0;
	private int contDaño=0;
	private String nombre="vacio";
	
	private String nombrar() {
		String nombre;
		if (this.tamaño==5) {
			nombre="Portaviones";
		} else if (this.tamaño==4) {
			nombre="Acorazado";
		} else if (this.tamaño==3) {
			nombre="Submarino";
		} else { 
			nombre="Lancha";
		}	
		return nombre;
	}
	
	public Barco(int n) {
		if (6>n && n>1) {
			this.contDaño=0;
			this.tamaño=n;
			this.nombre=nombrar();
		}
	}
	
	public int GetTamaño() {
		return this.tamaño;
	}
		
	
	public boolean Esta_destruido() {
		if (this.contDaño==this.tamaño) {
		return  true;
		}else{
			return false;
		}
	}
	
	public void SetNombre(String n) {
		this.nombre=n;
	}
	public String GetNombre() {
		return this.nombre;
	}

	
	public void incrementarDaño() {
		this.contDaño = 1 + contDaño;
	}
}
	

