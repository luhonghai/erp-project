package org.openswing.swing.pivottable.client;

import org.openswing.swing.pivottable.java.*;
import org.openswing.swing.message.receive.java.Response;


/**
 * <p>Title: OpenSwing Framework</p>
 * <p>Description: Pivot table controller: invoked by PivotTable to generate PivotTableModel through PivotTableEngine.</p>
 * <p>Copyright: Copyright (C) 2006 Mauro Carniel</p>
 *
 * <p> This file is part of OpenSwing Framework.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the (LGPL) Lesser General Public
 * License as published by the Free Software Foundation;
 *
 *                GNU LESSER GENERAL PUBLIC LICENSE
 *                 Version 2.1, February 1999
 *
 * This library is distributed in the hope that it will be useful,
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
public interface PivotTableController {

  /**
   * Callback invoked by PivotTable to retrieve PivotTableModel.
   * @param pars parameters used in PivotTableEngine to generate the PivotTableModel
   * @return VOResponse that contains a PivotTableModel generated starting from PivotTableParameters; ErrorResponse in case of errors on analyzing data
   */
  public Response getPivotTableModel(PivotTableParameters pars);


}
