package battleship.modelo;

public class jugador {
	private TableroBarcos BarcosJugador;
	private	TableroRadar RadarJugador;
	private String nombre;
	
	public jugador(String Nombre){
		BarcosJugador=new TableroBarcos();
		RadarJugador=new TableroRadar();
		this.setNombre(Nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TableroBarcos getBarcosJugador() {
		return BarcosJugador;
	}


	public TableroRadar getRadarJugador() {
		return RadarJugador;
	}

}
