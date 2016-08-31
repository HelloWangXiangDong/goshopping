package StringTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by XDStation on 2016/8/12 0012.
 */
public class Test {

    @org.junit.Test
    public void testString(){
        List<Integer> list = new ArrayList(Arrays.asList(1,2,3,4,5));
        int a = 44;
        if(list.contains(a)){
            System.out.println("包含！");
        }else {
            System.out.println("不包含！");
        }
    }
}
