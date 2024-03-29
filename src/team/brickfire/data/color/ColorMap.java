package team.brickfire.data.color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Maps {@link Color colors} to compassionate for problems with the sensor</p>
 * <p>Calculates a prioritized {@link Color color} to compensate for problems with the sensor</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class ColorMap {

    protected final Map<Color, Color> valueMapping;
    protected final Map<Color, Integer> priorities;

    /**
     * <p>Creates a ColorMap</p>
     *
     * @param valueMapping Mapping of colors
     * @param priorities Priorities of colors
     */
    public ColorMap(Map<Color, Color> valueMapping, Map<Color, Integer> priorities) {
        this.valueMapping = valueMapping;
        this.priorities = priorities;
    }

    /**
     * <p>Creates a ColorMap <br>
     * Can be used when another map inherits this and adds the values in its contractor</p>
     */
    protected ColorMap() {
        this.valueMapping = new HashMap<>();
        this.priorities = new HashMap<>();
    }

    /**
     * <p>Returns an array of the mapped {@link Color color} values</p>
     * <p>It maps each input value to its in the constructor given mapped color and returns this array.</p>
     *
     * @param values Values to map
     * @return Mapped values
     */
    public Color[] mappedValues(Color... values) {
        Color[] mapped = new Color[values.length];
        for (int i = 0; i < values.length; i++) {
            mapped[i] = valueMapping.get(values[i]);
        }
        return mapped;
    }

    /**
     * <p>Returns the most prioritized {@link Color color}. <br>
     * This gets calculated by summing the priorities of each value and returning the one with the highest priority</p>
     *
     * @param values Colors to calculate in the process
     * @return Prioritized color
     */
    public Color getPrioritisedValueBySum(Color... values) {
        Map<Color, Integer> prioritySum = new HashMap<>();
        for (Color v : values) {
            int temp = prioritySum.containsKey(v) ? prioritySum.get(v) : 0;
            prioritySum.remove(v);
            prioritySum.put(v, temp + priorities.get(v));
        }

        Color maxvalue = null;
        int maxPriority = -1;
        for (Color k : prioritySum.keySet()) {
            if (prioritySum.get(k) > maxPriority) {
                maxPriority = prioritySum.get(k);
                maxvalue = k;
            }
        }
        return maxvalue;
    }

    /**
     * <p>Chooses the {@link Color color} with the highest priority. <br>
     * It only checks for the single color and ignores how often a color appears</p>
     *
     * @param values Colors to check
     * @return Color with the highest priority
     */
    public Color valueWithMaxPriority(Color ... values) {
        Color maxvalue = null;
        int maxPriority = -1;
        for (Color v : values) {
            if (priorities.get(v) > maxPriority) {
                maxPriority = priorities.get(v);
                maxvalue = v;
            }
        }
        return maxvalue;
    }

    /**
     * <p>Returns the {@link Color color} repeated as often as its priority says</p>
     * @param values Values to repeat
     * @return The values
     */
    public Color[] repeatByPriority(Color ... values) {
        List<Color> colors = new ArrayList<>();
        for (Color value : values) {
            for (int i = 0; i < priorities.get(value); i++) {
                colors.add(value);
            }
        }
        return colors.toArray(new Color[0]);
    }

}
