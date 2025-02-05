public class UrineTestReport extends PatientReport {
    private String test = "Urine Test";
    private int UrineTest1;
    private int UrineTest2;
    private int UrineTest3;
    private Double averageUrineSugar;
    private String urineTestStatus;

    public UrineTestReport(String name, int patientId, String gender, String dateOfBirth, String address, String emergencyContactName, String emergencyContactNumber, double averageUrineSugar, int UrineTest1, int UrineTest2, int UrineTest3, String urineTestStatus) {
        super(name, patientId, gender, dateOfBirth, address, emergencyContactName, emergencyContactNumber);
        this.averageUrineSugar = averageUrineSugar;
        this.UrineTest1 = UrineTest1;
        this.UrineTest2 = UrineTest2;
        this.urineTestStatus = urineTestStatus;
        this.UrineTest3 = UrineTest3;
    }

    public String getType() {
        return test;
    }

    public int getFirst() {
        return UrineTest1;
    }

    public int getSecond() {
        return UrineTest2;
    }

    public int getThird() {
        return UrineTest3;
    }

    public double getAverageUrineSugar() {
        return averageUrineSugar;
    }

    public String getStatus() {
        return urineTestStatus;
    }

    public double getAverage() {
        return averageUrineSugar;
    }

    public void setUrineTest1(int urineTest1) {
        this.UrineTest1 = urineTest1;
    }

    public void setUrineTest2(int urineTest2) {
        this.UrineTest2 = urineTest2;
    }

    public void setUrineTest3(int urineTest3) {
        this.UrineTest3 = urineTest3;
    }

    public void setAverageUrineSugar(double averageUrineSugar) {
        this.averageUrineSugar = averageUrineSugar;
    }

    public void setUrineTestStatus(String urineTestStatus) {
        this.urineTestStatus = urineTestStatus;
    }
}
