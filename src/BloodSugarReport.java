public class BloodSugarReport extends PatientReport {
    private String test = "Blood Sugar Test";
    private int bloodSugarTest1;
    private int bloodSugarTest2;
    private int bloodSugarTest3;
    private Double averageBloodSugar;
    private String bloodSugarStatus;

    public BloodSugarReport(String name, int patientId, String gender, String dateOfBirth, String address, String emergencyContactName, String emergencyContactNumber, double averageBloodSugar, int bloodSugarTest1, int bloodSugarTest2, int bloodSugarTest3, String bloodSugarStatus) {
        super(name, patientId, gender, dateOfBirth, address, emergencyContactName, emergencyContactNumber);
        this.averageBloodSugar = averageBloodSugar;
        this.bloodSugarTest1 = bloodSugarTest1;
        this.bloodSugarTest2 = bloodSugarTest2;
        this.bloodSugarStatus = bloodSugarStatus;
        this.bloodSugarTest3 = bloodSugarTest3;
    }

    public String getType() {
        return test;
    }

    public int getFirst() {
        return bloodSugarTest1;
    }

    public int getSecond() {
        return bloodSugarTest2;
    }

    public int getThird() {
        return bloodSugarTest3;
    }

    public double getAverage() {
        return averageBloodSugar;
    }

    public String getStatus() {
        return bloodSugarStatus;
    }


    public void setAverageBloodSugar(double averageBloodSugar) {
        this.averageBloodSugar = averageBloodSugar;
    }

    public void setBloodSugarStatus(String bloodSugarStatus) {
        this.bloodSugarStatus = bloodSugarStatus;
    }
}
