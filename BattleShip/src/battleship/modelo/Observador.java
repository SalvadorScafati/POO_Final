package battleship.modelo;



public interface Observador {
	void notificar(EventosBatallaNaval evento);
	void notificarmensaje(String mensaje);
	void verCambiosEnTablero(String name);
	void notificaDisparo(String j, int f, int c);

}
