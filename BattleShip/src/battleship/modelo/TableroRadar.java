package battleship.modelo;

public class TableroRadar extends Tablero {


	public TableroRadar () {
		super();

	}
	
	
	public String hacerDisparo(int f,int c,TableroBarcos tablero) {
		String mensaje;
		String valor = "";
		mensaje=tablero.RecibirDisparo(f,c);
		if (mensaje=="barco dañado"||mensaje=="Portaviones"||
				mensaje=="Acorazado"||mensaje=="Submarino"||mensaje=="Crusero"||mensaje=="Lancha") {
			valor="barco";
			this.SetCasilla(f, c, valor);
			if (mensaje!="barco dañado") {
				mensaje="El barco "+mensaje+ " fue destruido";}
		} else if (mensaje=="agua"){
			valor="agua";
			this.SetCasilla(f, c, valor);
		}
		this.GetCasilla(f, c).casillaDescubierta();
		return mensaje;
	}




}
