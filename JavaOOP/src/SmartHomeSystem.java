import java.util.*;

public class SmartHomeSystem {
    private HashMap<String, Device> devices;

    public SmartHomeSystem() {
        devices = new HashMap<>();
    }

    public void addDevice(Device device) {
        devices.put(device.getDeviceId(), device);
    }

    public void removeDevice(String deviceId) {
        devices.remove(deviceId);
    }

    public Device getDevice(String deviceId) {
        return devices.get(deviceId);
    }

    public List<Device> getAllDevices() {
        return new ArrayList<>(devices.values());
    }
}