package metier;

public class Message {
	public Message(int id, int idDestinataire, int idEmetteur, String date, String message, int status, String objet) {
		this.id = id;
		this.idDestinataire = idDestinataire;
		this.idEmetteur = idEmetteur;
		this.date = date;
		this.message = message;
		this.status = status;
		this.objet = objet;
	}

	private int id;
	private int idDestinataire;
	private int idEmetteur;
	private String date;
	private String message;
	private int status;
	private String objet;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdDestinataire() {
		return idDestinataire;
	}

	public void setIdDestinataire(int idDestinataire) {
		this.idDestinataire = idDestinataire;
	}

	public int getIdEmetteur() {
		return idEmetteur;
	}

	public void setIdEmetteur(int idEmetteur) {
		this.idEmetteur = idEmetteur;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}
	public String toString(){
		String toReturn = id + " - " + objet;
		return toReturn;
	}

}
