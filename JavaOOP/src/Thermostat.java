public class Thermostat extends Device {
    private double temperature;

    public Thermostat(String deviceId, String deviceName, boolean isOn, double temperature) {
        super(deviceId, deviceName, isOn);
        this.temperature = temperature;
    }


    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return super.toString() + ", Temperature: " + temperature + "°C";
    }
}
