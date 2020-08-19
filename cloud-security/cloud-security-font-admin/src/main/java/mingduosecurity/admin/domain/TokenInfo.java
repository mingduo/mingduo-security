/**
 * 
 */
package mingduosecurity.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jojo
 *
 */
@Data
public class TokenInfo {

	private String access_token;
	private String refresh_token;
	private String token_type;
	private Long expires_in;
	private String scope;
	
	private LocalDateTime expireTime;
	
	public TokenInfo init() {
		expireTime = LocalDateTime.now().plusSeconds(expires_in - 3);
		return this;
	}

	@JsonIgnore
	public boolean isExpired() {
		return expireTime.isBefore(LocalDateTime.now());
	}
	
}
