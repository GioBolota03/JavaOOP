public class SecurityCamera extends Device {
    private String resolution;

    public SecurityCamera(String deviceId, String deviceName, boolean isOn, String resolution) {
        super(deviceId, deviceName, isOn);
        this.resolution = resolution;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return super.toString() + ", Resolution: " + resolution;
    }
}
