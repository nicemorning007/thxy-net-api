package cc.lyceum.api.thxy.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class NetVerify extends NetClient {

    private final String urlHost = "http://10.0.8.9:8080/portal/";

    public String login(String usernumber, String password) {
        password = Base64.encode(password.getBytes());
        // headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/plain, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8");

        // form
        Map<String, String> forms = new HashMap<>();
        forms.put("userName", usernumber);
        forms.put("userPwd", password);
        forms.put("userDynamicPwd", "");
        forms.put("userDynamicPwdd", "");
        forms.put("serviceType", "gdthxy");
        forms.put("isSavePwd", "on");
        forms.put("userurl", "");
        forms.put("userip", "");
        forms.put("basip", "");
        forms.put("language", "Chinese");
        forms.put("usermac", "null");
        forms.put("wlannasid", "");
        forms.put("wlanssid", "");
        forms.put("entrance", "null");
        forms.put("loginVerifyCode", "");
        forms.put("userDynamicPwddd", "");
        forms.put("customPageId", "0");
        forms.put("pwdMode", "0");
        forms.put("portalProxyIP", "10.0.8.9");
        forms.put("portalProxyPort", "50200");
        forms.put("dcPwdNeedEncrypt", "1");
        forms.put("assignIpType", "0");
        forms.put("appRootUrl", "http%3A%2F%2F10.0.8.9%3A8080%2Fportal%2F");
        forms.put("manualUrl", "");
        forms.put("manualUrlEncryptKey", "");

        String resultJosn = super.postBody(urlHost + "pws?t=li", headers, forms);

        return resposeHandle(resultJosn);
    }

    private String resposeHandle(String respose) {
        try {
            String s = URLDecoder.decode(new String(java.util.Base64.getDecoder().decode(respose), "UTF-8"), "UTF-8");
            if (s.contains("e_d")) {
                JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                String e_d = jsonObject.get("e_d").getAsString();
                s = jsonObject.get(e_d).getAsString();
                return s;
            } else {
                return "";
            }
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public void logout() {
        // headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/plain, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8");

        super.getBody(urlHost + "pws?t=lo", headers);
    }

}
