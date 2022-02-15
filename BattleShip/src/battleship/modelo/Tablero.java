package battleship.modelo;


public class Tablero {
	
	private casilla [] [] matriz=new casilla[10][10];
	
	
	Barco[] BarcosLista=new Barco[5];
	
	public Tablero() {
		int i=0;
		int j=0;
		while (matriz.length>i) {
			while (matriz.length>j) {
				this.matriz[i][j]=new casilla();
				j ++;
			}
			j=0;
			i++;
		}
	}
	
	

	public casilla GetCasilla(int f,int c) {
		if ((f>9 || f<0) || (c>9 || c<0)){
			return null;
		} else {
		return this.matriz[f][c];
		}
	}
	
	protected void SetCasilla(int f,int c, String valor) {
		this.matriz[f][c].setValor(valor);
	}
}
