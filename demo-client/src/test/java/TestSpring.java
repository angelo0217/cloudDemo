import com.demo.client.DemoClientApplication;
import com.demo.client.service.PrintService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Consumer;

import static com.demo.client.util.JasyptUtil.decyptPwd;
import static com.demo.client.util.JasyptUtil.encyptPwd;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestSpring {

    @Value("${jasypt.encryptor.password}")
    private String encodePwd;

    @Value("${test.db.encode}")
    private String testStr;

    @Value("${my.profile.data}")
    private String myData;

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

    @Test
    public void generatorPwd(){
        System.out.println(encodePwd);
        // 加密
        String encPwd = encyptPwd(encodePwd, "123456");
        // 解密
        String decPwd = decyptPwd(encodePwd, encPwd);
        System.out.println(encPwd);
        System.out.println(decPwd);

        System.out.println(testStr);
        System.out.println(myData);
    }

}