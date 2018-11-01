package org.rapidpm.vaadin.v10.issuetracker;

import static java.lang.System.setProperty;

import org.apache.meecrowave.Meecrowave;

public class VaadinWebUIRunner {

//  public MessageSource messageSource() {
//    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//    messageSource.setDefaultEncoding("UTF-8");
//
//    try {
//      PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//      Resource[] resources = resolver.getResources("classpath*:/i18n/**-messages**.properties");
//
//      String[] basenames = Arrays
//          .stream(resources)
//          .map(Resource::getFilename)
//          .map(fileName -> fileName.substring(0, fileName.indexOf("-messages")))
//          .map(baseName -> "classpath:/i18n/" + baseName + "-messages")
//          .distinct()
//          .toArray(String[]::new);
//
//      messageSource.setBasenames(basenames);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    return messageSource;
//  }


  public static void main(String[] args) throws Exception {

    setProperty("vaadin.i18n.provider", VaadinI18NProvider.class.getName());

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
