public class RatingData {
    private double receptionTreating;
    private double labTreating;
    private double interfaceUsability;
    private double privacyAndSecurity;
    private String notes;

    public RatingData(double receptionTreating, double labTreating, double interfaceUsability, double privacyAndSecurity, String notes) {
        this.receptionTreating = receptionTreating;
        this.labTreating = labTreating;
        this.interfaceUsability = interfaceUsability;
        this.privacyAndSecurity = privacyAndSecurity;
        this.notes = notes;
    }

    public double getReceptionTreating() {
        return receptionTreating;
    }

    public double getLabTreating() {
        return labTreating;
    }

    public double getInterfaceUsability() {
        return interfaceUsability;
    }

    public double getPrivacyAndSecurity() {
        return privacyAndSecurity;
    }

    public String getNotes() {
        return notes;
    }
}
