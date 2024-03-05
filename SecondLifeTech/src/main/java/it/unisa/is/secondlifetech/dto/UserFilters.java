package it.unisa.is.secondlifetech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserFilters {
	public static int MIN_STRING_LENGTH = 3;
	public static int MAX_STRING_LENGTH = 50;

	private String email;
	private String role;

	public UserFilters(String email, String role) {
		if (email != null && !email.isEmpty())
			this.email = email;
		if (role != null && !role.isEmpty())
			this.role = role;
	}

	public String toQueryString() {
		StringBuilder queryString = new StringBuilder();
		if (email != null)
			queryString.append("email=").append(email).append("&");

		if (role != null)
			queryString.append("role=").append(role);

		// Rimuovi l'ultimo "&"
		if (!queryString.isEmpty())
			if(queryString.charAt(queryString.length() - 1) == '&')
				queryString.setLength(queryString.length() - 1);

		return queryString.toString();
	}

	public boolean isDefault() {
		return email == null && role == null;
	}

}
