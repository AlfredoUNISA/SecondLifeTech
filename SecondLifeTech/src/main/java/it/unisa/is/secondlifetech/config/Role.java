package it.unisa.is.secondlifetech.config;

/**
 * Classe contenente i ruoli degli utenti.
 */
public class Role {
	public static final String CLIENTE = "ROLE_CLIENTE";
	public static final String GESTORE_UTENTI = "ROLE_GESTORE_UTENTI";
	public static final String GESTORE_PRODOTTI = "ROLE_GESTORE_PRODOTTI";
	public static final String GESTORE_ORDINI = "ROLE_GESTORE_ORDINI";
	public static final String[] ALL_ROLES = {
		getRoleName(CLIENTE),
		getRoleName(GESTORE_UTENTI),
		getRoleName(GESTORE_PRODOTTI),
		getRoleName(GESTORE_ORDINI)
	};

	/**
	 * Restituisce il nome del ruolo senza il prefisso "ROLE_".
	 *
	 * @param role il ruolo da cui ottenere il nome
	 * @return il nome del ruolo
	 */
	public static String getRoleName(String role) {
		return role.replace("ROLE_", "");
	}
}