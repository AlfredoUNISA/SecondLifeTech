package it.unisa.is.secondlifetech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OrderFilters {
    public static int MIN_STRING_LENGTH = 3;
    public static int MAX_STRING_LENGTH = 50;

    private String email;
    private String state;

    public OrderFilters(String email, String state) {
        if (email != null && !email.isEmpty())
            this.email = email;
        if (state != null && !state.isEmpty())
            this.state = state;
    }

    public String toQueryString() {
        StringBuilder queryString = new StringBuilder();
        if (!email.isBlank())
            queryString.append("email=").append(email).append("&");

        if (!state.isBlank())
            queryString.append("state=").append(state);

        // Rimuovi l'ultimo "&"
        if (!queryString.isEmpty())
            if(queryString.charAt(queryString.length() - 1) == '&')
                queryString.setLength(queryString.length() - 1);

        return queryString.toString();
    }

    public boolean isDefault() {
        return email == null && state == null;
    }

}
