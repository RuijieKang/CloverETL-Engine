/* Generated By:JJTree: Do not edit this line. CLVFDateDiffNode.java */

package org.jetel.interpreter.ASTnode;

import java.util.Calendar;

import org.jetel.interpreter.ExpParser;
import org.jetel.interpreter.TransformLangParserVisitor;

public class CLVFDateDiffNode extends SimpleNode {
	
    public int calendarField;
    public Calendar start,end;
	
	public CLVFDateDiffNode(int id) {
		super(id);
		start=Calendar.getInstance();
		end=Calendar.getInstance();
	}
	
	public CLVFDateDiffNode(ExpParser p, int id) {
		super(p, id);
		start=Calendar.getInstance();
		end=Calendar.getInstance();
	}
	
	
	/** Accept the visitor. **/
	public Object jjtAccept(TransformLangParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
	
    public void setCalendarField(String fieldName){
        if (fieldName.equalsIgnoreCase("year")){
            calendarField=Calendar.YEAR;
        }else if (fieldName.equalsIgnoreCase("month")){
            calendarField=Calendar.MONTH;
        }else if (fieldName.equalsIgnoreCase("week")){
            calendarField=Calendar.WEEK_OF_YEAR;
        }else if (fieldName.equalsIgnoreCase("day")){
                calendarField=Calendar.DAY_OF_MONTH;
        }else if (fieldName.equalsIgnoreCase("hour")){
             calendarField=Calendar.HOUR_OF_DAY;
        }else if (fieldName.equalsIgnoreCase("minute")){
             calendarField=Calendar.MINUTE;
        }else if (fieldName.equalsIgnoreCase("sec") || fieldName.equalsIgnoreCase("second") ){
            calendarField=Calendar.SECOND;
        }else{
            calendarField=Calendar.MILLISECOND;
        }
    }
}
