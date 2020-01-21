package mingduosecurity.admin.properties;

import lombok.Data;
import mingduosecurity.admin.domain.TokenInfoStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @apiNode:
 * @since 2020/1/21
 * @author : weizc 
 */
@Data
@Component
@ConfigurationProperties(prefix = "font.admin")
public class AdminProperties {

    TokenInfoStrategy storeStrategy=TokenInfoStrategy.SESSION;
}
