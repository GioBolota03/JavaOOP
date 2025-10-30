public class Device {
    private String deviceId;
    private String deviceName;
    private boolean isOn;

    public Device(String deviceId, String deviceName, boolean isOn) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.isOn = isOn;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public boolean isOn() {
        return isOn;
    }

    public void toggle() {
        isOn = !isOn;
    }

    @Override
    public String toString() {
        return deviceName + " (" + (isOn ? "ON" : "OFF") + ")";
    }
}
