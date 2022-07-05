import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestFunction {

    interface IsSameData{
        boolean isSame(String a, String b);
    }

    @Test
    public void test() {
        Function strLen = t -> {
            System.out.println(t);
            return t;
        };

        Consumer cc = c -> {
            System.out.println("consumer: " + c);
        };

        Predicate isDoubleMax = o -> {
            if (o.equals("Test")) {
                return true;
            }
            return false;
        };

        strLen.apply("aaaaa");
        cc.accept("bbbbb");
        System.out.println(isDoubleMax.test("Test"));

        IsSameData isSameData = (a, b) -> {
            return a.equals(b);
        };

        System.out.println(isSameData.isSame("A", "A"));
    }

}
