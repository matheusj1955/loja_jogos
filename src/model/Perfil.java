package model;

public enum Perfil {
	ADMINISTRADOR(1, "Administrador"), 
	FUNCIONARIO(2, "Funcion�rio"), 
	USUARIO(3, "Usu�rio");
	
	private int id;
	private String label;
	
	Perfil(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static Perfil valueOf(int id) {
		for (Perfil perfil : values()) {
			if (id == perfil.getId())
				return perfil;
		}
		return null;
	}
}
