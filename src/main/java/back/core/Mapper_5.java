package back.core;

import back.model.FormulaTupla;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

// Parametrizar con los tipos de keyInput, ,valueInput, keyoutput, valueOutput
public class Mapper_5 implements Mapper<String, Votacion, String, FormulaTupla> {

	public void map(String keyinput, Votacion valueinput, Context<String, FormulaTupla> context) {
		System.out.println(String.format("Llega KeyInput: %s con ValueInput: %s", keyinput, valueinput));

		FormulaTupla valueoutput = new FormulaTupla(valueinput.getVotos(), valueinput.getFormula());
		context.emit(valueinput.getDistrito(), valueoutput);

		System.out.println(String.format("Se emite (%s, %s)", valueinput.getDistrito(), valueoutput));
	}

}