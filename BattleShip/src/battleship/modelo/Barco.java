package battleship.modelo;


public class Barco {

	private  int tama�o=0;
	private int contDa�o=0;
	private String nombre="vacio";
	
	private String nombrar() {
		String nombre;
		if (this.tama�o==5) {
			nombre="Portaviones";
		} else if (this.tama�o==4) {
			nombre="Acorazado";
		} else if (this.tama�o==3) {
			nombre="Submarino";
		} else { 
			nombre="Lancha";
		}	
		return nombre;
	}
	
	public Barco(int n) {
		if (6>n && n>1) {
			this.contDa�o=0;
			this.tama�o=n;
			this.nombre=nombrar();
		}
	}
	
	public int GetTama�o() {
		return this.tama�o;
	}
		
	
	public boolean Esta_destruido() {
		if (this.contDa�o==this.tama�o) {
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

	
	public void incrementarDa�o() {
		this.contDa�o = 1 + contDa�o;
	}
}
	

