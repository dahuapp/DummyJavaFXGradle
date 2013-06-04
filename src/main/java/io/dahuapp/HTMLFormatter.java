/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dahuapp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 *
 * @author Jean-Baptiste
 */
    
public class HTMLFormatter extends Formatter {
   // formatage d’une ligne
   @Override
   public String format(LogRecord record) {
      StringBuilder s = new StringBuilder(1000);
      Date d = new Date(record.getMillis());
      DateFormat df = DateFormat.getDateTimeInstance(
                          DateFormat.LONG, DateFormat.MEDIUM, Locale.FRANCE);  
      s.append("<tr><td>");
      s.append(df.format(d));
      s.append("</td>");
      String color = "color: rgb(0, 0, 0)";
      if(record.getLevel() == Level.SEVERE) {
          color = "color: rgb(204, 0, 0)";
      } else if(record.getLevel() == Level.WARNING) {
          color = "color: rgb(204, 204, 0)";
      } else if(record.getLevel() == Level.FINE) {
          color = "color: rgb(0, 0, 204)";
      }
      s.append("<td><span  style=\"font-family:" + " Courier New,Courier,monospace; ").append(color).append(";\">" + "<b>");
      s.append(record.getLevel().getName());
      s.append(formatMessage(record));
      s.append("</b></span></td></tr>\n");
      return s.toString();
   }
   // début du fichier de log
   @Override
   public String getHead(Handler h) {
      return "<html>\n<body>\n<table>\n";
   }
   // fin du fichier de log
   @Override
   public String getTail(Handler h) {
       return "</table>\n</body>\n</html>\n";
   }
}
