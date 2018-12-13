package junit.com.vaadin.tutorial.issues.webapp;

import org.apache.meecrowave.Meecrowave;

public class VaadinWebUIRunner {

  public static void main(String[] args) throws Exception {


    new Meecrowave(new Meecrowave.Builder() {
      {
//        randomHttpPort();
        setHttpPort(8080);
        setTomcatScanning(true);
        setTomcatAutoSetup(true);
        setHttp2(true);

      }
    })
        .bake()
        .await();
  }
}
