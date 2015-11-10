package back.core;

import back.model.FormulaTupla;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Reducer_5 implements ReducerFactory<String, FormulaTupla, FormulaTupla> {

	public Reducer<FormulaTupla, FormulaTupla> newReducer(final String distrito) {
		return new Reducer<FormulaTupla, FormulaTupla>() {
			private FormulaTupla max;

			public void beginReduce() // una sola vez en cada instancia
			{
				max = new FormulaTupla(-1, "");
			}

			public void reduce(FormulaTupla value) {
				if (max.compareTo(value) < 1)
					max = value;
			}

			public FormulaTupla finalizeReduce() {
				System.out.println(String.format("FinalReduce for %s = %s", distrito, max));
				return max;
			}
		};
	}

}
