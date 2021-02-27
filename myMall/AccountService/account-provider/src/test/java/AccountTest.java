import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.zsl.account.RequestAndResponse.CheckRequest;
import com.zsl.account.RequestAndResponse.CheckResponse;
import com.zsl.account.RequestAndResponse.LoginRequest;
import com.zsl.account.RequestAndResponse.LoginResponse;
import com.zsl.account.UserAccountService;
import com.zsl.account.entitys.UserAccount;
import com.zsl.persistence.AccountManage;
import com.zsl.service.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.zsl.AccountProviderApplication;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 18:42
 * Description: No Description
 */
@SpringBootTest(classes = AccountProviderApplication.class)
public class AccountTest {

    @Autowired
    private AccountManage accountManage;

    @Autowired
    private LoginServiceImpl loginService;

    @Test
    public void test(){
        System.out.println(accountManage.findAllUserAccount());
    }

    @Test
    public void login() throws Exception {
        UserAccount account = new UserAccount();
        account.setAccount("123");
        account.setPassword("123");
        account.setName("123");
        LoginRequest request = new LoginRequest();
//        request.setCallbackUrl("backUrl_test");
        request.setUserAccount(account);
        System.out.println(loginService);
        LoginResponse loginResponse = loginService.login(request);
        System.out.println("token: " + loginResponse.getToken());
        System.out.println("message: " + loginResponse.getMsg());
    }

    @Test
    public void check() throws Exception {
        CheckRequest request = new CheckRequest();
        request.setToken("96edc63702e7433cb6aeac41e040f02e");
        CheckResponse response = loginService.check(request);
        System.out.println(response.getAccount());
    }
}
