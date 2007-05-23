/* Generated By:JJTree: Do not edit this line. CLVFSequenceNode.java */

package org.jetel.interpreter.ASTnode;

import org.jetel.data.sequence.Sequence;
import org.jetel.interpreter.TransformLangParser;
import org.jetel.interpreter.TransformLangParserVisitor;

public class CLVFSequenceNode extends SimpleNode {
    
    public static final int OP_NEXT=0;
    public static final int OP_CURRENT=1;
    public static final int OP_RESET=2;
    
    public String sequenceName;
    public int opType=0; // default
    public int retType=0; //default
    public Sequence sequence;
    
  public CLVFSequenceNode(int id) {
    super(id);
  }

  public CLVFSequenceNode(TransformLangParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(TransformLangParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  public void setName(String name){
      this.sequenceName=name;
  }
  
  public void setOperation(int op){
      this.opType=op;
  }
  
  public void setReturnType(int retType){
      this.retType=retType;
  }
}
