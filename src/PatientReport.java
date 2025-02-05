import javafx.beans.property.*;

import java.sql.Date;

public class PatientReport {
    private StringProperty name;
    private StringProperty dateOfBirth;
    private IntegerProperty patientId;
    private StringProperty gender;
    private StringProperty address, emergencyContactName, emergencyContactNumber;

    public PatientReport(String name, int patientId, String gender, String dateOfBirth, String address, String emergencyContactName, String emergencyContactNumber) {
        this.name = new SimpleStringProperty(name);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.gender = new SimpleStringProperty(gender);
        this.address = new SimpleStringProperty(address);
        this.emergencyContactName = new SimpleStringProperty(emergencyContactName);
        this.emergencyContactNumber = new SimpleStringProperty(emergencyContactNumber);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
    }

    public String getName() {
        return name.get();
    }

    public int getPatientId() {
        return patientId.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getEmergencyContactName() {
        return emergencyContactName.get();
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber.get();
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public void setPatientId(int patientId) {
        this.patientId.set(patientId);
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName.set(emergencyContactName);
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber.set(emergencyContactNumber);
    }

    public void setName(String name) {
        this.name.set(name);
    }

}
