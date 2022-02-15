package battleship.vista;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import battleship.controlador.BatallaNavalController;

public class vistaConsola implements IVista {
	public String name;
	private Boolean enSetup=false;
	private BatallaNavalController miControlador;
	private JFrame gameFrame;
	private JTextArea console;
	private JTextArea input;
	private JTextArea t;
	private JScrollPane scrollInput;
	private boolean SetupCombate=true;
	private String[][] disparos=new String[10][10];

	
	public vistaConsola(String name) {
		this.name=name;
		this.crearMatrizDisparos();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void scanRadar(int f, int c) {
		this.miControlador.Disparar(f, c);
		
	}
	

	@Override
	public void setShip(int f, int c) {
		this.console.append("\n Fase de colocar barcos en el tablero");
		this.console.append("\n Ingrese opcion");
		this.console.append("\n 1. Portaviones Tamaño 5 ");
		this.console.append("\n 2. Acorazado Tamaño 4 ");
		this.console.append("\n 3. Crusero Tamaño 3 ");
		this.console.append("\n 4. Submarino Tamaño 3 ");
		this.console.append("\n 5. Lancha Tamaño 2 ");
		this.console.append("\n 6. Colocar todos los barcos random ");
		int tam=this.input(1,6);
		
		if (tam==6) {
			this.miControlador.colocarRandom();
		}else {
			if (tam==1) {
				tam=5;
			}else if (tam==2) {
				tam=4;
			}else if (tam==3) {
				tam=3;
			}else if (tam==4) {
				tam=3;
			}else if (tam==5){
				tam=2;
			}	
		this.console.append("\n Ingrese opcion");
		this.console.append("\n 1. Horizontal ");
		this.console.append("\n 2. Vertical ");	
		String Direccion;
		int d=this.input(1,2);
		if (d==1) {
			Direccion="horizontal";
		}else {
			Direccion="vertical";
		}
		this.console.append("\n Ingrese Fila");
		f=this.input(0,9);
		this.console.append("\n Ingrese columna ");
		c=this.input(0,9);
		this.console.append("\n");
		this.miControlador.AgregarUnBarco(f, c, Direccion,tam ,this.name);
		}
	}
	
	public void crearMatrizDisparos() {
		int f=0;
		int c=0;
		while (f<=9) {
			while (c<=9) {
				this.disparos[f][c]="";
				c++;
			}
			f++;
			c=0;
		}
	}

	@Override
	public void setupBarcos() {
		if (enSetup==false) {
		this.enSetup=true;
		String[][]  grid=this.miControlador.TableroBarcos();
		int f=0;
		int c=0;
		this.console.append("\n ");
		this.console.append("\n | BARCOS |");
		this.console.append("\n ");
		this.console.append("\n   | 0| 1| 2| 3|  4|  5|  6| 7| 8| 9");
		while (f<=9) {
			this.console.append("\n"+f+"|");
			while (c<=9) {
				if (this.disparos[f][c].equals("x")) {
					this.console.append(" #|");
				}else if (grid[f][c].equals("agua")) {
					this.console.append(" >|");
				}else {
				   if (grid[f][c].equals("Portaviones")) {
						this.console.append(" P|");
					}else if (grid[f][c].equals("Acorazado")) {
						this.console.append(" A|");
					}else if (grid[f][c].equals("Crusero")) {
						this.console.append(" C|");
					}else if (grid[f][c].equals("Submarino")) {
						this.console.append(" S|");
					}else if (grid[f][c].equals("Lancha")) {
						this.console.append(" L|");
					}
					
				}
				c++;
			}
			f++;
			c=0;
		}
		this.enSetup=false;
		if (this.miControlador.combateFase==false) {
			this.setShip(0,0);
		}
		}
		this.console.append("\n");
	}

	public int input(int min, int max) {
		this.t.setText("");
		this.input.setText("");
		Boolean loop=true;
		int i=0;
		this.input.setEditable(true);
		while (loop) {
			if (this.t==null) {
			}else if (this.t.getText().equals("")==false) {
			String someString = this.t.getText().replaceAll(" ", "").replaceAll("\n", "");
			i=this.lectorInt(min, max, someString);
			boolean isNumeric = (-1==i);
			if (isNumeric) {
				this.t.setText("");
			}else {
				loop=false;
				this.t.setText("");
			}
			}
		}
		this.input.setEditable(false);
		return i;
	}

	
	@Override
	public void Menu() {
		
		this.gameFrame=new JFrame();
		this.gameFrame.setSize(810,450);
		this.gameFrame.setLayout(null);  
		this.gameFrame.setVisible(true);
		this.console=new JTextArea();
		this.input=new JTextArea();
		this.t=new JTextArea();
		this.t.setText(".");
		this.input.setBounds(0,390,790,30);
		this.scrollInput = new JScrollPane (this.console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollInput.setBounds(0,0,790,390);
		this.gameFrame.add(scrollInput);  
		this.console.setEditable(false);
		this.input.setEditable(true);
		this.gameFrame.add(this.input);
        this.input.setEditable(false);
        this.input.addKeyListener(new KeyListener(){
        	 
        	  public void keyPressed(KeyEvent e) {
        		  if (e.getKeyCode()==KeyEvent.VK_ENTER){
        			    t.setText(input.getText());
        			    input.setVisible(false);
        			    input.setVisible(true);
        			    input.setText("");
        			  }
        	  }

			@Override
			public void keyTyped(KeyEvent e) {
		
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}});
        this.input.setBackground(Color.black);
        this.input.setForeground(Color.WHITE);
        this.console.setBackground(Color.BLACK);
        this.console.setForeground(Color.WHITE);
        this.console.append("BATALLA NAVAL MENU");
        this.console.append("\n 1: Versus");
        this.console.append("\n 2: Versus IA");
        this.console.append("\n");
        this.console.append("\n Ingrese opcion:");
        int valor=this.input(1,2);
	    if (valor==1) {
	    	this.readyMenu();
	    }else if (valor==2) {
	    	this.readyVersusIA();
	    }
	}

	private int lectorInt(int min,int max,String s) {
	   int i= -1;
	   try {
            i = Integer.parseInt(s);
            if (max<i) {
            	i=-1;
            	this.console.append("\n valor no pertenece al rango de opciones!");
            }
            if (min>i) {
            	i=-1;
            	this.console.append("\n valor no pertenece al rango de opciones!");
            }
	   } catch(NumberFormatException nfe) {
		   this.console.append("\n Ingrese devuelta!");
	   }
	   if ((i==-1)==false) {
		   this.console.append("\n"+i);
	   }
	   
		return i;
	}

	@Override
	public void readyMenu() {
		this.miControlador.readyPlayerVersus(this.name);
	}
	
	@Override
	public void readyVersusIA() {
		this.miControlador.iniciarversusIA(this.name);
	}

	@Override
	public void setMiControlador(BatallaNavalController batallaNavalController) {
		this.miControlador=batallaNavalController;
		
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		this.console.append("\n ");
		this.console.append("INFO : "+mensaje);
		this.console.append("\n ");
		
	}

	@Override
	public void finPartida(String winner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarLabels() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarBarcos() {
		if (enSetup==false) {
			this.setupBarcos();
		}		
	}

	@Override
	public void setupCombate() {
		if (this.SetupCombate) {
			this.SetupCombate=false;
			this.console.append("\n ");
			this.console.append("\n --------------------");
			this.console.append("\n | fase de combate   |");
			this.console.append("\n --------------------");
			this.actualizarRadar();
		}
		
	}

	private void preguntarDisparo() {
		this.console.append("\n Disparar en las coordenadas: ");
		this.console.append("\n Ingrese Fila");
		int f=this.input(0,9);
		this.console.append("\n Ingrese columna ");
		int c=this.input(0,9);
		this.console.append("\n");
		this.scanRadar(f, c);
		
	}

	@Override
	public void actualizarRadar() {
		if (this.miControlador.Turnode().equals(this.name)) {
		this.console.append("\n ");
		this.console.append("\n | RADAR |");
		this.console.append("\n ");
		String[][]  grid=this.miControlador.TableroRadar(this.name);
		int f=0;
		int c=0;
		this.console.append("\n  | 0| 1| 2| 3| 4| 5| 6| 7| 8| 9");
		while (f<=9) {
			this.console.append("\n"+f+"|");
			while (c<=9) {
				if (grid[f][c].equals("agua")) {
					this.console.append(">>|");
				}else if (grid[f][c].equals("")) {
					this.console.append(" - |");
				}else {
					this.console.append(" X|");
				}
				c++;
			}
			f++;
			c=0;
		}
		this.preguntarDisparo();
		}
	}

	@Override
	public void marcarDisparo(int f, int c) {
		this.disparos[f][c]="x";
		this.console.append("\n Oponente disparo en "+f+"x"+c);
	}


	
}
