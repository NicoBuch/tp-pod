package back.model;


import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class FormulaTupla implements DataSerializable, Comparable<FormulaTupla>
{
    private int votos;
    private String formula;

    public FormulaTupla() {
    }

    public FormulaTupla(int theVotos, String theFormula) {
        this.votos = theVotos;
        this.formula = theFormula;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int theVotos) {
        this.votos = theVotos;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String theFormula) {
        this.formula= theFormula;
    }

     public void writeData(ObjectDataOutput out)
            throws IOException {

        out.writeInt(votos);
        out.writeUTF(formula);
    }

     public void readData(ObjectDataInput in)
            throws IOException {

        votos = in.readInt();
        formula = in.readUTF();
    }
    
     public String toString() {
    	
    	return String.format("Formula: %s con %d votos", formula, votos);
    }

	public int compareTo(FormulaTupla other) {
		return getVotos() - other.getVotos();
	}
}
