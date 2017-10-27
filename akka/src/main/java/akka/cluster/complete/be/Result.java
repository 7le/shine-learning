package akka.cluster.complete.be;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author 7le
 * @Description: 结果
 * @date 2017年10月27日
 * @since v1.0.0
 */
public class Result implements Serializable {

    public final int n;
    public final BigInteger factorial;

    Result(int n, BigInteger factorial) {
        this.n = n;
        this.factorial = factorial;
    }

    @Override
    public String toString() {
        return "Result{" +
                "n=" + n +
                ", factorial=" + factorial +
                '}';
    }

}
