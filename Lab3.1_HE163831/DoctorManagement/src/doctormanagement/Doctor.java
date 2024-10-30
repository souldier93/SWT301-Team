package doctormanagement;

public class Doctor {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String dateOfBirth;
    private String specialization;
    private int availability;
    private String email;
    private String mobile;

    public Doctor(String name, String dateOfBirth, String specialization, int availability, String email, String mobile) {
        setId();
        setName(name);
        setDateOfBirth(dateOfBirth);
        setSpecialization(specialization);
        setAvailability(availability);
        setEmail(email);
        setMobile(mobile);
    }

    // Auto-incremented ID
    private void setId() {
        this.id = idCounter++;
    }

    // Getters and setters with validation
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty() || name.length() > 50) {
            throw new IllegalArgumentException("Name must be between 1 and 50 characters.");
        }
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || !dateOfBirth.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new IllegalArgumentException("Date of birth must be in the format dd/MM/yyyy.");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.length() > 255) {
            throw new IllegalArgumentException("Specialization must be less than 255 characters.");
        }
        this.specialization = specialization;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        if (availability < 0 || availability > 3) {
            throw new IllegalArgumentException("Availability must be between 0 and 3.");
        }
        this.availability = availability;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        if (!mobile.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            throw new IllegalArgumentException("Mobile number must be in the format (000)-000-0000.");
        }
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "Doctor ID: " + id + "\nName: " + name + "\nDate of Birth: " + dateOfBirth + 
               "\nSpecialization: " + specialization + "\nAvailability: " + availability + 
               "\nEmail: " + email + "\nMobile: " + mobile;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Doctor doctor = (Doctor) obj;

        if (id != doctor.id) return false;
        if (!name.equals(doctor.name)) return false;
        if (!dateOfBirth.equals(doctor.dateOfBirth)) return false;
        if (!specialization.equals(doctor.specialization)) return false;
        if (!email.equals(doctor.email)) return false;
        return mobile.equals(doctor.mobile);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + specialization.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + mobile.hashCode();
        return result;
    }
}
