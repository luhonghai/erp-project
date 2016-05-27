package org.jallinone.commons.client;


import com.luhonghai.jetty.JettyRunner;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.openswing.swing.util.server.FileHelper;

import javax.swing.*;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Application main class: this is the first class called.</p>
 * <p>Copyright: Copyright (C) 2006 Mauro Carniel</p>
 *
 * <p> This file is part of JAllInOne ERP/CRM application.
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the (LGPL) Lesser General Public
 * License as published by the Free Software Foundation;
 *
 *                GNU LESSER GENERAL PUBLIC LICENSE
 *                 Version 2.1, February 1999
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 *       The author may be contacted at:
 *           maurocarniel@tin.it</p>
 *
 * @author Mauro Carniel
 * @version 1.0
 */
public class ClientApplication extends ClientApplet {
  /**
   * Method called by Java Web Start to init the application.
   */
  public static void main(String[] argv) {
    FileHelper.BASE_CLASS = ClientApplication.class;
    int port = 9999;
    if (argv != null && argv.length > 0) {
      port = Integer.parseInt(argv[0]);
    }
    final JettyRunner jettyRunner = new JettyRunner(port);
    try {
      jettyRunner.start(new AbstractLifeCycle.AbstractLifeCycleListener() {
        @Override
        public void lifeCycleFailure(LifeCycle event, Throwable cause) {
          super.lifeCycleFailure(event, cause);
        }

        @Override
        public void lifeCycleStarted(LifeCycle event) {
          String controller = jettyRunner.getBaseURL() + "/controller";
          System.out.print("Base URL " + controller);
          System.setProperty("SERVERURL", controller);
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              new ClientApplication();
            }
          });
        }

        @Override
        public void lifeCycleStarting(LifeCycle event) {
          super.lifeCycleStarting(event);
        }

        @Override
        public void lifeCycleStopped(LifeCycle event) {
          super.lifeCycleStopped(event);
        }

        @Override
        public void lifeCycleStopping(LifeCycle event) {
          super.lifeCycleStopping(event);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  public ClientApplication() {
    calledAsApplet = false;
    initApplication();
  }


}
