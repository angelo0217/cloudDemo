import com.demo.client.DemoClientApplication;
import com.demo.client.service.PrintService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestSpring {

    @Autowired
    private PrintService printService;

    private Consumer sayHello = new Consumer<>() {
        @Override
        public void accept(Object o) {
            printService.print((String) o);
        }
    };

    @Test
    public void getVideo(){
        sayHello.accept("11111");
    }
}