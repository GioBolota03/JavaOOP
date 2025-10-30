public abstract class SmartDevice {
    private String deviceId;
    private String deviceName;
    private boolean isOn;

    public SmartDevice(String deviceId, String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.isOn = false;
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

    public void turnOn() {
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
    }

    public abstract void performAction();

    @Override
    public String toString() {
        return "Device ID: " + deviceId + ", Name: " + deviceName + ", Status: " + (isOn ? "On" : "Off");
    }
}
