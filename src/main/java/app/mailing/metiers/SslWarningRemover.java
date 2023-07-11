package app.mailing.metiers;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


@Slf4j
@Component
public class SslWarningRemover {

    public SslWarningRemover() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            
                        }
                        
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            
                        }
                    }
            };
            
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            
        } catch (Exception e) {
        }
        
    }
}