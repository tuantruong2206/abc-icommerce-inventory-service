package support;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
public abstract class BaseControllerTest extends BaseTest {
    protected MockMvc mockMvc;
}
