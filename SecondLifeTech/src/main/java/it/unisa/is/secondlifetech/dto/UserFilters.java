package it.unisa.is.secondlifetech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserFilters {
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
			queryString.append("email=").append(email);

		if (role != null)
			queryString.append("role=").append(role);

		// Rimuovi l'ultimo "&"
		if (!queryString.isEmpty())
			queryString.setLength(queryString.length() - 1);

		return queryString.toString();
	}

	public boolean isDefault() {
		return email == null && role == null;
	}

}
