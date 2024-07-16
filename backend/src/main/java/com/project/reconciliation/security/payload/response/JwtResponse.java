package com.project.reconciliation.security.payload.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private Integer id;
	private String username;
	private String email;
	@Exclude
	private String role;

	public JwtResponse(String accessToken, Integer id, String username, String email, String role) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}
}
