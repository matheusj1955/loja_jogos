package model;

public enum TipoLoja {
	DIGITAL(1, "Digital"), 
	FISICO(2, "Fisico");
	
	private int id;
	private String label;
	
	TipoLoja(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoLoja valueOf(int id) {
		for (TipoLoja tipo : values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}
}