package org.jallinone.system.customizations.server;

import org.openswing.swing.server.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.openswing.swing.message.receive.java.*;
import java.sql.*;
import org.openswing.swing.logger.server.Logger;
import org.jallinone.system.server.JAIOUserSessionParameters;
import java.math.BigDecimal;
import org.openswing.swing.message.send.java.GridParams;
import org.jallinone.system.customizations.java.ReportVO;
import org.jallinone.commons.java.ApplicationConsts;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.jallinone.system.customizations.java.ReportFileNameVO;


/**
 * <p>Title: JAllInOne ERP/CRM application</p>
 * <p>Description: Action class used to check if files uploaded from client already exist in to "report" subfolder.</p>
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
public class CheckReportFilesAction implements Action {


  public CheckReportFilesAction() {
  }

  /**
   * @return request name
   */
  public final String getRequestName() {
    return "checkReportFiles";
  }


  /**
   * Business logic to execute.
   */
  public final Response executeCommand(Object inputPar,UserSessionParameters userSessionPars,HttpServletRequest request, HttpServletResponse response,HttpSession userSession,ServletContext context) {
    try {
      String path = org.openswing.swing.util.server.FileHelper.getRootResource()+"reports/";
      Hashtable bytes = (Hashtable)inputPar;
      Enumeration en = bytes.keys();
      String fileName = null;
      while(en.hasMoreElements()) {
        fileName = en.nextElement().toString();
        if (new File(path+fileName).exists())
          return new VOResponse(Boolean.FALSE);
      }
      return new VOResponse(Boolean.TRUE);
    }
    catch (Throwable ex) {
      Logger.error(userSessionPars.getUsername(),this.getClass().getName(),"executeCommand","Error while checking uploaded report files",ex);
      return new ErrorResponse(ex.getMessage());
    }
  }



}
