package application;

public class Imei {

	public Imei(String name, String imei, String kartennummer) {
		this.name = name.toUpperCase();
		this.imei = imei.toUpperCase();
		this.kartennummer = kartennummer.toUpperCase();
	}

	public Imei(String value) {
		this.name = value.toUpperCase();
		this.imei = value.toUpperCase();
		this.kartennummer = value.toUpperCase();
	}

	private String name;
	private String imei;
	private String kartennummer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getKartennummer() {
		return kartennummer;
	}

	public void setKartennummer(String kartennummer) {
		this.kartennummer = kartennummer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imei == null) ? 0 : imei.hashCode());
		result = prime * result + ((kartennummer == null) ? 0 : kartennummer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || this == null)
			return false;
		
		//System.out.println(this.kartennummer);

		boolean b = false;
		if (o instanceof Imei) {
			Imei toCompare = (Imei) o;
			if (!this.kartennummer.isEmpty() && this.kartennummer.equals(toCompare.kartennummer))
				b = true;
			if (!this.imei.isEmpty() && this.imei.equals(toCompare.imei))
				b = true;
			if (!this.name.isEmpty() && this.name.equals(toCompare.name))
				b = true;
		}
		return b;
	}

}
