package teemo.mastermind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class MastermindServiceTest {

    private String secret;
    private String test;
    private String expectedResult;

    public MastermindServiceTest(String secret, String test, String expectedResult) {
        this.secret = secret;
        this.test = test;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection useCases() {
        return Arrays.asList(new Object[][] {
                { "1243", "1254", "++-" },
                { "1243", "2143", "++--" },
                { "7734", "1270", "-" },
                { "1234", "2002", "-" },
                { "1234", "2200", "+" },
                { "3129", "1249", "+--" },
                { "1234", "1234", "++++" },
                { "2234", "2234", "++++" }
        });
    }

    @Test
    public void testUseCase(){
        MastermindService service = new MastermindService(this.secret);
        assertThat(service.compareInputWithSecret(this.test)).isEqualTo(this.expectedResult);
    }
}
