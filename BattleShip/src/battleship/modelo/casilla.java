package battleship.modelo;

public class casilla {
	private String valor;
	private boolean explorada;
	
	public casilla() {
		this.explorada=false;
		this.valor="agua";
	}
	
	public String GetValor() {
		return this.valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isExplorada() {
		return explorada;
	}

	public void casillaDescubierta() {
		this.explorada=true;
	}
}
