
import com.config.DemoConfigApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoConfigApplication.class)
public class Test {

    @Autowired
    private StringEncryptor stringEncryptor;

    @org.junit.Test
    public void testEncrypt(){
        try {
//            System.out.println(stringEncryptor.encrypt("Aa123456"));
            System.out.println(stringEncryptor.decrypt("jft31GY7Iojyuo4hBHkZSEsdjY6Y+Fdc"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}