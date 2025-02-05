public class BloodPressureReport extends PatientReport {
    private String test = "Blood Pressure Test";
    private int bloodPressureTest1;
    private int bloodPressureTest2;
    private int bloodPressureTest3;
    private Double averageBloodPressure;
    private String bloodPressureStatus;

    public BloodPressureReport(String name, int patientId, String gender, String dateOfBirth, String address, String emergencyContactName, String emergencyContactNumber, double averageBloodPressure, int bloodPressureTest1, int bloodPressureTest2, int bloodPressureTest3, String bloodPressureStatus) {
        super(name, patientId, gender, dateOfBirth, address, emergencyContactName, emergencyContactNumber);
        this.averageBloodPressure = averageBloodPressure;
        this.bloodPressureTest1 = bloodPressureTest1;
        this.bloodPressureTest2 = bloodPressureTest2;
        this.bloodPressureStatus = bloodPressureStatus;
        this.bloodPressureTest3 = bloodPressureTest3;
    }

    public String getType() {
        return test;
    }

    public int getFirst() {
        return bloodPressureTest1;
    }

    public int getSecond() {
        return bloodPressureTest2;
    }

    public int getThird() {
        return bloodPressureTest3;
    }

    public double getAverage() {
        return averageBloodPressure;
    }

    public String getStatus() {
        return bloodPressureStatus;
    }

    public void setBloodPressureTest1(int bloodPressureTest1) {
        this.bloodPressureTest1 = bloodPressureTest1;
    }

    public void setBloodPressureTest2(int bloodPressureTest2) {
        this.bloodPressureTest2 = bloodPressureTest2;
    }

    public void setBloodPressureTest3(int bloodPressureTest3) {
        this.bloodPressureTest3 = bloodPressureTest3;
    }

    public void setAverageBloodPressure(double averageBloodPressure) {
        this.averageBloodPressure = averageBloodPressure;
    }

    public void setBloodPressureStatus(String bloodPressureStatus) {
        this.bloodPressureStatus = bloodPressureStatus;
    }
}
