package org.jallinone.commons.client;

import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.mdi.client.MDIFrame;

import java.util.HashMap;
//import org.openswing.swing.util.client.HessianObjectSender;


public class AdminClientApplication extends ClientApplication {
  public AdminClientApplication() {
  }

  public String getParameter(String p) {
    if (p.equals("SERVERURL"))
      return "http://localhost:8080/controller";
    else return p;
  }


  /**
   * Method that initialize the client side application.
   */
  protected void initApplication() {
//    ClientUtils.setObjectSender(new HessianObjectSender());

    // define domains...
    Domain colTypeSYS12Domain = new Domain("COLUMN_TYPE");
    colTypeSYS12Domain.addDomainPair("S","text");
    colTypeSYS12Domain.addDomainPair("D","date");
    colTypeSYS12Domain.addDomainPair("N","numeric");
    domains.put(
      colTypeSYS12Domain.getDomainId(),
      colTypeSYS12Domain
    );

    Domain languagesDomain = new Domain("LANGUAGES");
    languagesDomain.addDomainPair("EN","english");
    languagesDomain.addDomainPair("IT","italian");
    languagesDomain.addDomainPair("ES","espanol");
		languagesDomain.addDomainPair("PTBR","brazilian");
		languagesDomain.addDomainPair("DE","german");
		languagesDomain.addDomainPair("HR","croatian");
		languagesDomain.addDomainPair("RU","russian");
    domains.put(
      languagesDomain.getDomainId(),
      languagesDomain
    );

    HashMap map = new HashMap();
    map.put("username","ADMIN");
    map.put("password","admin");
    try {
      authenticateUser(map);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Method called by Java Web Start to init the application.
   */
  public static void main(String[] argv) {
    System.setProperty("SERVERURL","http://localhost:8080/controller");
    AdminClientApplication a = new AdminClientApplication();
    try {
      MDIFrame m = new MDIFrame(a);
      m.setVisible(true);
    }
    catch (Throwable ex) {
      ex.printStackTrace();
    }
  }


}
