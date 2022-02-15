package battleship.vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import battleship.controlador.BatallaNavalController;


public class vistaGrafica implements IVista {
	private String name;
	private BatallaNavalController miControlador;
	//----Message -------
	private JLabel messageLabel;
	//--- Main menu -----
	private JButton versusPlayerButton;
	private JButton versusAIButton;
	private JFrame menuFrame;
	//--- Game	--------
	private JFrame gameFrame;
	private JButton matrizBarcos[][]=new JButton [10][10];
	private JButton matrizRadar[][]=new JButton [10][10];
	private JRadioButton radioB1;
	private JRadioButton radioB2;
	private JRadioButton radioB3;
	private JRadioButton radioB4;
	private JRadioButton radioB5;
	private JRadioButton radioDirectionH;
	private JRadioButton radioDirectionV;
	private ButtonGroup shipsGroup;
	private ButtonGroup directionGroup;
	//---- Combat ------
	private JLabel turnoLabel;
	private JLabel barcosDestruidosLabel;
	private JLabel barcosDestruidosOponenteLabel;
	private JButton buttonRandom;
	
	public vistaGrafica(String name) {
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void scanRadar(int f,int c) {
		this.miControlador.Disparar(f, c);
	}
	
	@Override
	public void setShip(int f,int c) {
		int shipSize=0;
		String Direction="null";
		if (this.radioB1.isSelected()) {
			shipSize=5;
		}else if (this.radioB2.isSelected()) {
			shipSize=4;
		}else if (this.radioB3.isSelected()) {
			shipSize=3;
		}else if (this.radioB4.isSelected()) {
			shipSize=3;
		}else if (this.radioB5.isSelected()) {
			shipSize=2;
		}
		
		if (this.radioDirectionH.isSelected()) {
			Direction="horizontal";
		}else if (this.radioDirectionV.isSelected()) {
			Direction="vertical";
		}
		if (shipSize==0 && Direction.equals("null")) 
			return;
		this.miControlador.AgregarUnBarco(f, c, Direction,shipSize ,this.name);

	}
	
	public void colocarRandom() {
		this.miControlador.colocarRandom();
		this.buttonRandom.setVisible(false);
	}

	@Override
	public void setupBarcos() {
		this.menuFrame.setVisible(false);
		this.gameFrame = new JFrame();
		this.gameFrame.setSize(1200,750);
		this.gameFrame.setLayout(null);  
		this.gameFrame.setVisible(true);
		//Create labels
		JLabel lbname=new JLabel(this.name);
		lbname.setBounds(10,-10,100,50);
		this.gameFrame.add(lbname);
		this.messageLabel=new JLabel("Info:");
		this.messageLabel.setBounds(100,550,300,50);
		this.messageLabel.setForeground(new Color(153,0,76));
		this.gameFrame.add(this.messageLabel);
		JLabel lb1=new JLabel("RADAR");
		lb1.setBounds(50,10,50,50);
		this.gameFrame.add(lb1);
		JLabel lb2=new JLabel("BARCOS");
		lb2.setBounds(650,10,50,50);
		this.gameFrame.add(lb2);
		//Label turno
		this.turnoLabel=new JLabel();
		this.turnoLabel.setBounds(500, 0,200,50);
		this.turnoLabel.setForeground(Color.BLUE);
		this.gameFrame.add(this.turnoLabel);
		//Label barcos Destruidos;
		this.barcosDestruidosLabel=new JLabel();
		this.barcosDestruidosLabel.setBounds(20,610,550,50);
		this.barcosDestruidosLabel.setForeground(Color.red);
		this.gameFrame.add(this.barcosDestruidosLabel);
		//Label barcos Destruidos oponente;
		this.barcosDestruidosOponenteLabel=new JLabel();
		this.barcosDestruidosOponenteLabel.setBounds(620,610,550,50);
		this.barcosDestruidosOponenteLabel.setForeground(Color.red);
		this.gameFrame.add(this.barcosDestruidosOponenteLabel);
		//Crear button
		this.buttonRandom=new JButton("Colocar Random");
		this.buttonRandom.setBounds(500,0,200,20);
		this.buttonRandom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	colocarRandom();
        }});
		this.gameFrame.add(this.buttonRandom);
		//createGridBarcos
		int fila=0;
		int columna=0;
		while (fila<=9) {
			while (columna<=9) {
				this.matrizRadar[fila][columna]=new JButton(" ");
				this.matrizRadar[fila][columna].setName(fila+"-"+columna);
				this.matrizRadar[fila][columna].setBackground(Color.BLACK);
				this.matrizRadar[fila][columna].setBounds((columna+1)*50,(fila+1)*50,50,50);
				this.matrizRadar[fila][columna].setBorder(BorderFactory.createLineBorder(Color.green));
				this.matrizRadar[fila][columna].addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	JButton o = (JButton)e.getSource();
		            	String name = o.getName();
		            	String[] parts = name.split("-");
		            	String f=parts[0];
		            	String c=parts[1];
		            	scanRadar(Integer.parseInt(f),Integer.parseInt(c));
		        }});
				this.gameFrame.add(this.matrizRadar[fila][columna]);
				columna++;
			}
			columna=0;
			fila++;
		}
		//createGridRadar
		fila=0;
		columna=0;
		while (fila<=9) {
			while (columna<=9) {
				this.matrizBarcos[fila][columna]=new JButton(" ");
				this.matrizBarcos[fila][columna].setName(fila+"-"+columna);
				this.matrizBarcos[fila][columna].setBackground(Color.blue);
				this.matrizBarcos[fila][columna].setBounds((columna+1)*50+600,((fila+1)*50),50,50);
				this.matrizBarcos[fila][columna].addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	JButton o = (JButton)e.getSource();
		            	String name = o.getName();
		            	String[] parts = name.split("-");
		            	String f=parts[0];
		            	String c=parts[1];
		            	setShip(Integer.parseInt(f),Integer.parseInt(c));
		        }});
				this.gameFrame.add(this.matrizBarcos[fila][columna]);
				columna++;
			}
			columna=0;
			fila++;
		}
		//Panel buttons - fase 1 - setup the battleships 
		this.shipsGroup=new ButtonGroup();
		this.directionGroup=new ButtonGroup();
		this.radioDirectionH=new JRadioButton("Horizontal");
		this.radioDirectionH.setBounds(550,200,100,15);
		directionGroup.add(this.radioDirectionH);
		this.radioDirectionV=new JRadioButton("Vertical");
		this.radioDirectionV.setBounds(550,220,100,15);
		directionGroup.add(this.radioDirectionV);
		this.radioB1=new JRadioButton("Portaviones");
		radioB1.setBounds(550,50,100,15);
		shipsGroup.add(radioB1);
		this.radioB2=new JRadioButton("Acorazado");
		radioB2.setBounds(550,70,100,15);
		shipsGroup.add(radioB2);
		this.radioB3=new JRadioButton("Submarino");
		radioB3.setBounds(550,90,100,15);
		shipsGroup.add(radioB3);
		this.radioB4=new JRadioButton("Crusero");
		radioB4.setBounds(550,110,100,15);
		shipsGroup.add(radioB4);
		this.radioB5=new JRadioButton("Lancha");
		radioB5.setBounds(550,130,100,15);
		shipsGroup.add(radioB5);
		this.gameFrame.add(this.radioB1);
		this.gameFrame.add(this.radioB2);
		this.gameFrame.add(this.radioB3);
		this.gameFrame.add(this.radioB4);
		this.gameFrame.add(this.radioB5);
		this.gameFrame.add(this.radioDirectionH);
		this.gameFrame.add(this.radioDirectionV);

	}
	
	@Override
	public void Menu() {
		this.menuFrame=new JFrame();
		//-crear boton versus
		this.versusPlayerButton=new JButton("Versus");
		this.versusPlayerButton.setBounds(200,100,300,50);
		this.versusPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	readyMenu();
        }});
		this.menuFrame.add(versusPlayerButton);
		//
		//-crear boton versus IA
		this.versusAIButton=new JButton("Versus AI");
		this.versusAIButton.setBounds(200,200,300,50);
		this.versusAIButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	readyVersusIA();
        }});
		this.menuFrame.add(versusAIButton);
		//
		this.menuFrame.setSize(800,400);
		this.menuFrame.setLayout(null);  
		this.menuFrame.setVisible(true);
    }

	@Override
	public void readyMenu() {
		this.miControlador.readyPlayerVersus(this.name);
	}
	
	public void readyVersusIA() {
		this.miControlador.iniciarversusIA(this.name);
	}

	
	@Override
	public void setMiControlador(BatallaNavalController batallaNavalController) {
		this.miControlador=batallaNavalController;
		
	}


	@Override
	public void mostrarMensaje(String mensaje) {
		if (this.messageLabel==null) {
			return;
		}
		this.messageLabel.setText("INFO : "+ mensaje);
	}


	@Override
	public void finPartida(String winner) {
		this.mostrarMensaje(winner);
		
	}
	
	@Override
	public void actualizarLabels() {
		this.turnoLabel.setText("TURNO DE: "+this.miControlador.Turnode());
		this.barcosDestruidosLabel.setText("Barcos enemigos por destruir: "+this.miControlador.barcosDestruidos(this.name));
		this.barcosDestruidosOponenteLabel.setText("Mis barcos con vida: "+this.miControlador.barcosDestruidosOponente(this.name));

	}

	@Override
	public void actualizarBarcos() {
		if (this.miControlador.setupFase) {
		String[][]  grid=this.miControlador.TableroBarcos();
		int f=0;
		int c=0;
		while (f<=9) {
			while (c<=9) {
				if (grid[f][c].equals("agua")) {
					this.matrizBarcos[f][c].setBackground(Color.blue);
				}else {
					if (grid[f][c].equals("Portaviones")) {
						this.matrizBarcos[f][c].setBackground(new Color(150, 150, 0));
					}else if (grid[f][c].equals("Acorazado")) {
						this.matrizBarcos[f][c].setBackground(new Color(153,0,0));
					}else if (grid[f][c].equals("Crusero")) {
						this.matrizBarcos[f][c].setBackground(new Color(118, 117,117));
					}else if (grid[f][c].equals("Submarino")) {
						this.matrizBarcos[f][c].setBackground(new Color(156, 80, 0));
					}else if (grid[f][c].equals("Lancha")) {
						this.matrizBarcos[f][c].setBackground(new Color(0,103,53));
					}
					
				}
				c++;
			}
			f++;
			c=0;
		}}
		
	}

	@Override
	public void setupCombate() {
		this.buttonRandom.setVisible(false);
		this.radioB1.setVisible(false);
		this.radioB2.setVisible(false);
		this.radioB3.setVisible(false);
		this.radioB4.setVisible(false);
		this.radioB5.setVisible(false);
		this.radioDirectionH.setVisible(false);
		this.radioDirectionV.setVisible(false);
		this.actualizarLabels();
	}

	@Override
	public void actualizarRadar() {
		String[][]  grid=this.miControlador.TableroRadar(this.name);
		int f=0;
		int c=0;
		while (f<=9) {
			while (c<=9) {
				if (grid[f][c].equals("agua")) {
					this.matrizRadar[f][c].setBackground(Color.CYAN);
				}else if (grid[f][c].equals("")) {
					this.matrizRadar[f][c].setBackground(Color.black);
				}else {
					this.matrizRadar[f][c].setBackground(Color.green);
				}
				c++;
			}
			f++;
			c=0;
		}
	}

	@Override
	public void marcarDisparo(int f, int c) {
		this.matrizBarcos[f][c].setText("X");
		this.matrizBarcos[f][c].setForeground(Color.WHITE);
		
	}
		
	
}
