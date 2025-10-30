public class Light extends Device {
    private int brightness;

    public Light(String deviceId, String deviceName, int brightness) {
        super(deviceId, deviceName, false);
        this.brightness = brightness;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return super.toString() + ", Brightness: " + brightness + "%";
    }
}
