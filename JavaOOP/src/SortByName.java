import java.util.Comparator;

public class SortByName implements Comparator<Device> {
    @Override
    public int compare(Device d1, Device d2) {
        return d1.getDeviceName().compareTo(d2.getDeviceName());
    }
}

