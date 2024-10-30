package doctormanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DoctorManagement {
    private List<Doctor> doctors;
    private SimpleDateFormat dateFormat;

    public DoctorManagement() {
        doctors = new ArrayList<>();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false); // Strict date parsing
    }

    public static void main(String[] args) {
        DoctorManagement management = new DoctorManagement();
        management.addSampleDoctors(); // Add sample doctors
        management.start(); // Start the management system
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nDoctor Management System");
            System.out.println("1. Edit Doctor Information");
            System.out.println("2. Delete Doctor");
            System.out.println("3. Search Doctor by ID");
            System.out.println("4. Search Doctor by Name");
            System.out.println("5. Sort Doctors by Date of Birth");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    editDoctor(scanner);
                    break;
                case "2":
                    deleteDoctor(scanner);
                    break;
                case "3":
                    searchDoctorByID(scanner);
                    break;
                case "4":
                    searchDoctorByName(scanner);
                    break;
                case "5":
                    sortDoctorsByDateOfBirth();
                    break;
                case "6":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close(); // Close scanner to avoid resource leaks
    }

    public void addSampleDoctors() {
        // Adding sample doctors
        doctors.add(new Doctor("John Doe", "15/04/1985", "Cardiologist", 1, "john.doe@example.com", "(123)-456-7890"));
        doctors.add(new Doctor("Jane Smith", "20/11/1990", "Dermatologist", 2, "jane.smith@example.com", "(234)-567-8901"));
        doctors.add(new Doctor("Emily Johnson", "10/02/1980", "Pediatrician", 0, "emily.johnson@example.com", "(345)-678-9012"));
        System.out.println("Sample doctors added successfully.");
    }

    public void editDoctor(Scanner scanner) {
        int doctorId = inputDoctorId(scanner);
        Doctor doctor = findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        System.out.println("Editing Doctor with ID: " + doctor.getId());

        updateDoctorName(scanner, doctor);
        updateDoctorDOB(scanner, doctor);
        updateDoctorSpecialization(scanner, doctor);
        updateDoctorAvailability(scanner, doctor);
        updateDoctorEmail(scanner, doctor);
        updateDoctorMobile(scanner, doctor);

        System.out.println("Doctor updated successfully.");
    }

    private void updateDoctorName(Scanner scanner, Doctor doctor) {
        System.out.print("Enter new Name (or press Enter to keep the current one): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty() && name.length() <= 50) doctor.setName(name);
    }

    private void updateDoctorDOB(Scanner scanner, Doctor doctor) {
        System.out.print("Enter new Date of Birth (dd/MM/yyyy or press Enter to keep the current one): ");
        String dateOfBirth = scanner.nextLine().trim();
        if (!dateOfBirth.isEmpty() && isValidDate(dateOfBirth)) doctor.setDateOfBirth(dateOfBirth);
    }

    private void updateDoctorSpecialization(Scanner scanner, Doctor doctor) {
        System.out.print("Enter new Specialization (or press Enter to keep the current one): ");
        String specialization = scanner.nextLine().trim();
        if (!specialization.isEmpty() && specialization.length() <= 255) doctor.setSpecialization(specialization);
    }

    private void updateDoctorAvailability(Scanner scanner, Doctor doctor) {
        System.out.print("Enter new Availability (or press Enter to keep the current one): ");
        String availabilityStr = scanner.nextLine().trim();
        if (!availabilityStr.isEmpty()) {
            try {
                doctor.setAvailability(Integer.parseInt(availabilityStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid availability input. Please enter an integer.");
            }
        }
    }

    private void updateDoctorEmail(Scanner scanner, Doctor doctor) {
        System.out.print("Enter new Email (or press Enter to keep the current one): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty() && isValidEmail(email)) doctor.setEmail(email);
    }

    private void updateDoctorMobile(Scanner scanner, Doctor doctor) {
        System.out.print("Enter new Mobile (or press Enter to keep the current one): ");
        String mobile = scanner.nextLine().trim();
        if (!mobile.isEmpty() && isValidMobile(mobile)) doctor.setMobile(mobile);
    }

    public void deleteDoctor(Scanner scanner) {
        int doctorId = inputDoctorId(scanner);
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            doctors.remove(doctor);
            System.out.println("Doctor deleted successfully.");
        } else {
            System.out.println("Doctor not found.");
        }
    }

    public void searchDoctorByID(Scanner scanner) {
        int doctorId = inputDoctorId(scanner);
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            System.out.println(doctor);
        } else {
            System.out.println("Doctor not found.");
        }
    }

    public void searchDoctorByName(Scanner scanner) {
        System.out.print("Enter Doctor Name to search: ");
        String name = scanner.nextLine().trim();
        List<Doctor> result = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(doctor);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No doctors found with the name: " + name);
        } else {
            for (Doctor doctor : result) {
                System.out.println(doctor);
            }
        }
    }

    public void sortDoctorsByDateOfBirth() {
        doctors.sort(Comparator.comparing(doctor -> {
            try {
                return dateFormat.parse(doctor.getDateOfBirth());
            } catch (ParseException e) {
                return null;
            }
        }));
        System.out.println("Doctors sorted by date of birth.");
    }

    private Doctor findDoctorById(int id) {
        return doctors.stream().filter(doctor -> doctor.getId() == id).findFirst().orElse(null);
    }

    private int inputDoctorId(Scanner scanner) {
        System.out.print("Enter Doctor ID: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{2}/\\d{2}/\\d{4}");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    private boolean isValidMobile(String mobile) {
        return mobile.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}");
    }
}
