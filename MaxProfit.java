import java.util.*;
import java.util.stream.Collectors;

public class MaxProfit {
    public static void main(String[] args) {
        int t = 13; // Change time for other test cases

        // Creating object for the mentioned property with their build time and earning
        Property commercialParks = new Property("C", 10, 3000);
        Property theatre = new Property("T", 5, 1500);
        Property pub = new Property("P", 4, 1000);

        // Putting all object in map as value with key as code(T,C,P)
        Map<String, Property> propertyMapByCode = new HashMap<>();
        propertyMapByCode.put(commercialParks.getCode(), commercialParks);
        propertyMapByCode.put(theatre.getCode(), theatre);
        propertyMapByCode.put(pub.getCode(), pub);

        // Getting the property code in as sorted array order by build time to generate possible combination.
        String[] sortedPropCodesByTime = propertyMapByCode
                .values()
                .stream()
                .sorted(Comparator.comparingInt(Property::getBuildTime).reversed())
                .map(Property::getCode)
                .collect(Collectors.toList()).toArray(String[]::new);

        // Generating all possible combination and storing it for individual property which can be build single/multiple time.
        List<List<String>> allCombinations = new ArrayList<>();
        for (String s : sortedPropCodesByTime) {
            int sum = propertyMapByCode.get(s).getBuildTime();
            int count = 0;
            while (sum <= t) {
                count++;
                ArrayList<String> propCombination = new ArrayList<>();
                for (int j = 0; j < count; j++) {
                    propCombination.add(s);
                }
                allCombinations.add(propCombination);
                sum += propertyMapByCode.get(s).getBuildTime();
            }
        }

        // Generating all possible combination and storing it for different properties which can be build single/multiple time.
        for (int i = 0; i < sortedPropCodesByTime.length; i++) {
            for (int j = i+1; j < sortedPropCodesByTime.length; j++) {
                int sum = propertyMapByCode.get(sortedPropCodesByTime[i]).getBuildTime() + propertyMapByCode.get(sortedPropCodesByTime[j]).getBuildTime();
                int count = 0;
                while (sum <= t) {
                    count++;
                    ArrayList<String> propCombination = new ArrayList<>();
                    propCombination.add(sortedPropCodesByTime[i]);
                    for (int k = 0; k < count; k++) {
                        propCombination.add(sortedPropCodesByTime[j]);
                    }
                    allCombinations.add(propCombination);
                    sum+=propertyMapByCode.get(sortedPropCodesByTime[j]).getBuildTime();
                }
            }
        }

        // Calculating the profit of each combination and getting the max profit with their combination.
        int max = Integer.MIN_VALUE;
        List<List<String>> maxPropCombo = new ArrayList<>();
        for (List<String> propCombination: allCombinations) {
            int currTime = t;
            int earning = 0;
            for (String prop: propCombination) {
                currTime -= propertyMapByCode.get(prop).getBuildTime();
                earning += (propertyMapByCode.get(prop).getEarningPerUnit() * currTime);
            }
            if(earning >= max) {
                if(earning == max) {
                    maxPropCombo.add(propCombination);
                } else {
                    maxPropCombo.clear();
                    maxPropCombo.add(propCombination);
                }
                max = earning;
            }
        }

        // Printing Output
        System.out.println("Max Earning: " + max);
        maxPropCombo.forEach(MaxProfit::printFinalFormattedOutput);
    }

    // Function to print combination of properties in required format.
    static void printFinalFormattedOutput(List<String> propCombination) {
        int countOfT = 0;
        int countOfP = 0;
        int countOfC = 0;

        for (String code: propCombination) {
            if(code.equals("T"))
                countOfT++;
            if(code.equals("P"))
                countOfP++;
            if(code.equals("C"))
                countOfC++;
        }

        System.out.println("T:" + countOfT +" P:" + countOfP + " C:" + countOfC);
    }
}

class Property {
    private String code;
    private int buildTime;
    private int earningPerUnit;

    public Property(String code, int buildTime, int earningPerUnit) {
        this.code = code;
        this.buildTime = buildTime;
        this.earningPerUnit = earningPerUnit;
    }

    public String getCode() {
        return code;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public int getEarningPerUnit() {
        return earningPerUnit;
    }

    @Override
    public String toString() {
        return "Property{" +
                "code='" + code + '\'' +
                ", buildTime=" + buildTime +
                ", earningPerUnit=" + earningPerUnit +
                '}';
    }
}

