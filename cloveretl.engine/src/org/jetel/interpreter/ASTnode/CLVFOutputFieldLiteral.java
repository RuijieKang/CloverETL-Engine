/* Generated By:JJTree: Do not edit this line. CLVFOutputFieldLiteral.java */

package org.jetel.interpreter.ASTnode;
import org.jetel.interpreter.ExpParser;
import org.jetel.data.DataField;
import org.jetel.data.DataRecord;
import org.jetel.interpreter.TransformLangExecutorRuntimeException;
import org.jetel.interpreter.ParseException;
import org.jetel.interpreter.TransformLangParserVisitor;
import org.jetel.metadata.DataRecordMetadata;

public class CLVFOutputFieldLiteral extends SimpleNode {
  
    DataField field;
    int recordNo;
    int fieldNo;
    String fieldName;  
    
  public CLVFOutputFieldLiteral(int id) {
    super(id);
  }

  public CLVFOutputFieldLiteral(ExpParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(TransformLangParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  /**
   * Get field of input record (1st record)
   * 
     * @param fName
     * @throws ParseException
     */
    public void setFieldName(String fName) throws ParseException{
        // get rid of leading '$' character (the 1st character)
      recordNo=0;
      DataRecordMetadata record= parser.getInRecordMeta();
      if (record==null){
          throw new ParseException("Unknown data field ["+fName+"]");
      }
        fieldNo=record.getFieldPosition(fName.substring(1));
        if (fieldNo==-1){
            throw new ParseException("Unknown data field ["+fName+"]");
        }
    }
   public void setRecordFieldName(String fRecName) throws ParseException{
          // get rid of leading '$' character (the 1st character)
          String recFieldName[]=fRecName.substring(1).split("\\.");
          DataRecordMetadata record=parser.getInRecordMeta(parser.getInRecordNum(recFieldName[0]));
          if (record==null){
              throw new ParseException("Unknown data field ["+fRecName+"]"); 
          }
          fieldNo=record.getFieldPosition(recFieldName[1]);
          if (fieldNo==-1){
              throw new ParseException("Unknown data field ["+fRecName+"]");
          }
      }
   
   public void setRecordNumFieldName(String fRecName) throws ParseException{
       // get rid of leading '$' character (the 1st character)
       String recFieldName[]=fRecName.substring(1).split("\\.");
       DataRecordMetadata record=parser.getInRecordMeta(Integer.parseInt(recFieldName[0]));
       if (record==null){
           throw new ParseException("Unknown data field ["+fRecName+"]"); 
       }
       fieldNo=record.getFieldPosition(recFieldName[1]);
       if (fieldNo==-1){
           throw new ParseException("Unknown data field ["+fRecName+"]");
       }
   }   
   
   
   public void bindToField(DataRecord[] records){
       try{
           field=records[recordNo].getField(fieldNo);
       }catch(NullPointerException ex){
           throw new TransformLangExecutorRuntimeException("can't determine "+fieldName);
       }
   }
    
}
