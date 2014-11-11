package yukinami.example.saas.util;

import org.springframework.util.Assert;

/**
 * Created by snowblink on 14/11/7.
 */
public class TenantContextHolder {

    private static final ThreadLocal<String> contextHolder =
            new ThreadLocal<String>();

    public static void setBusinessName(String businessName) {
        Assert.notNull(businessName, "businessName cannot be null");
        contextHolder.set(businessName);
    }

    public static String getBusinessName() {
        return contextHolder.get();
    }

    public static void clearBusinessName() {
        contextHolder.remove();
    }
}
