import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class SmartHomeGUI {
    private SmartHomeSystem system;
    private JFrame frame;
    private DeviceTableModel tableModel;
    private JTable deviceTable;

    public SmartHomeGUI() {
        system = new SmartHomeSystem();
        frame = new JFrame("Smart Home Automation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        tableModel = new DeviceTableModel(system.getAllDevices());
        deviceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(deviceTable);

        JButton addButton = new JButton("Add Device");
        JButton removeButton = new JButton("Remove Device");
        JButton toggleButton = new JButton("Control Device");
        JButton statusButton = new JButton("Check Status");

        addButton.addActionListener(this::addDevice);
        removeButton.addActionListener(this::removeDevice);
        toggleButton.addActionListener(this::toggleDevice);
        statusButton.addActionListener(this::checkStatus);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(toggleButton);
        buttonPanel.add(statusButton);

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void addDevice(ActionEvent e) {
        String id = JOptionPane.showInputDialog("Enter Device ID:");
        String[] deviceTypes = {"Light", "Thermostat", "Camera", "Door Lock"};
        String name = (String) JOptionPane.showInputDialog(null, "Select Device Type:", "Device Type",
                JOptionPane.QUESTION_MESSAGE, null, deviceTypes, deviceTypes[0]);

        if (id != null && name != null) {
            boolean status = JOptionPane.showConfirmDialog(null, "Turn ON the device?", "Device Status",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

            Device device = new Device(id, name, status);
            system.addDevice(device);
            tableModel.updateData(system.getAllDevices());
        }
    }

    private void removeDevice(ActionEvent e) {
        int row = deviceTable.getSelectedRow();
        if (row >= 0) {
            String deviceId = (String) tableModel.getValueAt(row, 0);
            system.removeDevice(deviceId);
            tableModel.updateData(system.getAllDevices());
        } else {
            JOptionPane.showMessageDialog(frame, "Select a device to remove.");
        }
    }

    private void toggleDevice(ActionEvent e) {
        int row = deviceTable.getSelectedRow();
        if (row >= 0) {
            String deviceId = (String) tableModel.getValueAt(row, 0);
            Device device = system.getDevice(deviceId);
            if (device != null) {
                device.toggle();
                tableModel.updateData(system.getAllDevices());
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Select a device to control.");
        }
    }

    private void checkStatus(ActionEvent e) {
        int row = deviceTable.getSelectedRow();
        if (row >= 0) {
            String deviceId = (String) tableModel.getValueAt(row, 0);
            Device device = system.getDevice(deviceId);
            JOptionPane.showMessageDialog(frame, "Device Status: " + (device.isOn() ? "ON" : "OFF"));
        } else {
            JOptionPane.showMessageDialog(frame, "Select a device to check status.");
        }
    }

    public static void main(String[] args) {
        new SmartHomeGUI();
    }
}

class DeviceTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Device ID", "Device Name", "Status"};
    private List<Device> devices;

    public DeviceTableModel(List<Device> devices) {
        this.devices = new ArrayList<>(devices);
    }

    @Override
    public int getRowCount() {
        return devices.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Device device = devices.get(row);
        return switch (column) {
            case 0 -> device.getDeviceId();
            case 1 -> device.getDeviceName();
            case 2 -> device.isOn() ? "ON" : "OFF";
            default -> null;
        };
    }

    public void updateData(List<Device> newDevices) {
        devices = new ArrayList<>(newDevices);
        fireTableDataChanged();
    }
}
