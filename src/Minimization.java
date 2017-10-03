import java.lang.reflect.Array;
import java.util.*;

public class Minimization {
    private MooreMachines mMoore;
    public Minimization(MooreMachines moore) throws CloneNotSupportedException {
        mMoore = new MooreMachines(moore);
        //check copy constructor
        /*mMoore.getState().clear();
        mMoore.printTable();
        System.out.println("_______________");
        moore.printTable();*/
    }


    public MooreMachines getMinimizeMooreMachines() {
        MooreMachines resultMoore = new MooreMachines(mMoore);
        zeroEquivalence(resultMoore);

        Map<String, ArrayList<String>> oldEquivalence = new LinkedHashMap<String,  ArrayList<String>>();
        while(true) {
            Map<String, ArrayList<String>> newEquivalence = getEquivalent(resultMoore);
            newEquivalence.forEach((key, value) -> System.out.println(key + " : " + value));
            break;
        }

        return resultMoore;
    }

    private Map<String, ArrayList<String>> getEquivalent(MooreMachines resultMoore) {
        Map<String, ArrayList<String>> result = new LinkedHashMap<String, ArrayList<String>>();
        for(int i = 0; i < resultMoore.getState().size(); i++) {
            String key = resultMoore.getOutputSignals().get(i);

            for(ArrayList<String> row : resultMoore.getTable()) {
                key += "/" + row.get(i);
            }
            ArrayList<String> array = new ArrayList<String>();
            if(result.containsKey(key)) {
                array = new ArrayList<String>(result.get(key));
            }
            array.add(resultMoore.getState().get(i));
            result.put(key, array);
        }
        return result;
    }

    private void sort(MooreMachines resultMoore) {
        ArrayList<String> state = resultMoore.getState(); state.clear();
        ArrayList<String> outputSignal = resultMoore.getOutputSignals(); outputSignal.clear();
        ArrayList<ArrayList<String>> table = resultMoore.getTable(); table.clear();
        table = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < mMoore.getTable().size(); ++i) {
            table.add(new ArrayList<String>());
        }
        Set setOutSignalIndex = new HashSet();
        for(int index = 0; index < mMoore.getOutputSignals().size(); index++) {
            for(int outSignalIndex : getAllIndex(mMoore.getOutputSignals().get(index))) {
                if(!setOutSignalIndex.contains(outSignalIndex)) {
                    outputSignal.add(mMoore.getOutputSignals().get(index));
                    state.add("" + (outSignalIndex + 1));
                    for(int i = 0; i < table.size(); ++i) {
                        table.get(i).add(mMoore.getTable().get(i).get(outSignalIndex));
                    }
                    setOutSignalIndex.add(outSignalIndex);
                }
            }
        }
        for(int i = 0; i < table.size(); ++i) {
            resultMoore.getTable().add(table.get(i));
        }

        //System.out.println(resultMoore.getOutputSignals());
        //System.out.println(resultMoore.getState());
    }

    private void zeroEquivalence(MooreMachines resultMoore) {
        sort(resultMoore);
        for(int i = 0; i < resultMoore.getState().size(); ++i) {
            for(int j = 0; j < resultMoore.getTable().size(); ++j) {
                ArrayList<String> value = resultMoore.getTable().get(j);
                value.set(i, resultMoore.getOutputSignals().get(getIndexByStateName(value.get(i))));
            }
        }
        System.out.println("________~0 экв________");
        System.out.println(resultMoore.getOutputSignals());
        System.out.println(resultMoore.getState());
        for(ArrayList<String> array : resultMoore.getTable()) {
            System.out.println(array);
        }
        System.out.println("_______________________");
        //разбить
    }

    private int getIndexByStateName(String name)
    {
        for(String _item : mMoore.getState()) {
            if(_item.equals(name))
                return mMoore.getState().indexOf(_item);
        }
        return -1;
    }
    private ArrayList<Integer> getAllIndex(String name)
    {
        ArrayList<Integer> array = new ArrayList<Integer>();
        ArrayList<String> outSignals = mMoore.getOutputSignals();
        for(int i = 0; i < outSignals.size(); ++i) {
            if(outSignals.get(i).equals(name)) {
                array.add(i);
            }
        }
        return array;
    }
}
