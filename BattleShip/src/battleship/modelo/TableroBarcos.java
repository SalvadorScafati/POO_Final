package battleship.modelo;

import java.util.ArrayList;
import java.util.Collection;

public class TableroBarcos extends Tablero {

	private Collection <Barco> Barcos;
	
	public TableroBarcos () {
		super();
		this.Barcos=new ArrayList<Barco>();
	}
	
	public int cantidadBarcos() {
		return this.Barcos.size();
	}
	
	public String AgregarBarco(int f,int c,String direccion,Barco barco) {
		/*dirreccion horizontal o vertical*/
		if (barco.GetTamaño()==3) {
			if (BuscarBarco("Submarino").GetNombre()!="vacio") {
				barco.SetNombre("Crusero");
			}}
		if (existeBarco(barco.GetNombre())){
			String mensaje="BARCO DUPLICADO";
			return mensaje;
		}
		int i=0;
		int faux=f;
		int	caux=c;
		String mensaje;
		casilla cas;
		boolean noEntra=false;
		while (barco.GetTamaño()>i&&noEntra==false) {
			if (f>9||c>9) {
				noEntra=true;
			}else {
				cas=this.GetCasilla(f, c);
			if (cas.GetValor()!="agua") {
				noEntra=true;
			} else {
				i++;
				if (direccion.equals("horizontal")) {
					c++;
				}else if (direccion.equals("vertical")) {
					f++;}
					else {
						noEntra=true;
				}
			}
			}
		}
		if (noEntra) {
			mensaje="BARCO NO ENTRA EN EL TABLERO";
		}else {
			i=0;
			while (barco.GetTamaño()>i) {
				this.SetCasilla(faux, caux, barco.GetNombre());
				i++;
				if (direccion.equals("horizontal")) {
						caux++;
				}else {
						faux++;
					}
			}
			this.Barcos.add(barco);
			mensaje="BARCO ENTRO EN EL TABLERO";
		}
		return mensaje; 
	}
	
	public String barcoAfloteString() {
		Collection<Barco> lista=this.barcoAflote(); 
		String aux="";
		for (Barco i:lista) {
			aux=aux+" - "+i.GetNombre();
		}
		return aux;
	}
	
	public String barcosDestruidosString() {
		Collection<Barco> lista=this.barcosDestruidos(); 
		String aux="";
		for (Barco i:lista) {
			aux=aux+" - "+i.GetNombre();
		}
		return aux;
	}

	public Collection<Barco> barcoAflote() {
		
		Collection<Barco> Lista= new  ArrayList<Barco>();
		for (Barco i:Barcos) {
			if (i.Esta_destruido()==false) {
				Lista.add(i);;
			}
		}
		return Lista;
	}
	
	public Collection<Barco> barcosDestruidos() {
		
		Collection<Barco> Lista= new  ArrayList<Barco>();
		for (Barco i:Barcos) {
			if (i.Esta_destruido()==true) {
				Lista.add(i);;
			}
		}
		return Lista;
	}
	




	public String RecibirDisparo(int f, int c) {
		String mensaje;
		if (f>9||c>9) {
			mensaje="valor fuera de rango";
		} else {
			casilla casilla=this.GetCasilla(f,c);
		if (casilla.isExplorada()) {
			mensaje="casilla ya explorada";
		} else if (casilla.GetValor()=="agua") {
			this.GetCasilla(f, c).casillaDescubierta();
			mensaje="agua";
		} else {
			this.BuscarBarco(casilla.GetValor()).incrementarDaño();;
			this.GetCasilla(f, c).casillaDescubierta();
			if(this.BuscarBarco(casilla.GetValor()).Esta_destruido()) {
				mensaje=this.BuscarBarco(casilla.GetValor()).GetNombre();	
			}else {
				mensaje="barco dañado";
			}
		}
		}
		return mensaje;
	}

	private Boolean existeBarco(String nombre) {
		for (Barco i:Barcos) {
			if (i.GetNombre()==nombre) {
				return true;
			}
		}
		return false;
	}
	
	
	//crear crusero
	private Barco BuscarBarco(String nombre) {
		Barco aux=new Barco(3);
		aux.SetNombre("vacio");
		for (Barco i:Barcos) {
			if (i.GetNombre()==nombre) {
				aux=i;
			}
		}
		return aux;
	}

}

