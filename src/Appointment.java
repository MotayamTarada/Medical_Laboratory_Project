class Appointment {
    private int appointmentID;
    private String appointmentDate;
    private String appointmentTime;
    private String status;
    private int patientID;

    public Appointment(int appointmentID, String appointmentDate, String appointmentTime, String status, int patientID) {
        this.appointmentID = appointmentID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.patientID = patientID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
}
